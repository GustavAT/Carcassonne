package distudios.at.carcassonne.gui;

import android.content.Context;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.lobby.DeviceActionListener;
import distudios.at.carcassonne.networking.lobby.DeviceListFragment;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.lobby.MyDeviceItemRecyclerViewAdapter;
import distudios.at.carcassonne.networking.lobby.NetworkManager;
import distudios.at.carcassonne.networking.lobby.OnDeviceChangedEventListener;
import distudios.at.carcassonne.networking.lobby.OnWifiP2pDeviceActionEventListener;
import distudios.at.carcassonne.networking.lobby.WifiDirectBroadcastReceiver;

public class LobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, OnWifiP2pDeviceActionEventListener,
        WifiP2pManager.ConnectionInfoListener, DeviceActionListener {

    // network
    private WifiDirectBroadcastReceiver receiver;
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Toolbar toolbar = findViewById(R.id.toolbar_lobby);
        toolbar.setTitle(R.string.text_lobbies);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Get back to main activity", Toast.LENGTH_SHORT).show();
            }
        });

        WifiP2pManager.PeerListListener devices = (WifiP2pManager.PeerListListener) getSupportFragmentManager().findFragmentById(R.id.fragment_devices);
        networkManager = new NetworkManager(this, devices, this);
        networkManager.setOnDeviceChangedEventListener(new OnDeviceChangedEventListener() {
            @Override
            public void onEvent(WifiP2pDevice device) {
                // set device name and host address
                TextView deviceName = findViewById(R.id.text_device_name);
                deviceName.setText(device.deviceName);
                TextView hostAddress = findViewById(R.id.text_host_address);
                hostAddress.setText(device.deviceAddress);
            }
        });
        networkManager.discoverPeers();

        Button buttonDiscover = findViewById(R.id.button_discover);
        buttonDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                Toast.makeText(context, context.getString(R.string.text_search_for_peers), Toast.LENGTH_LONG).show();
                networkManager.discoverPeers();
            }
        });

        Button startGame = findViewById(R.id.button_startgame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh:
                Context context = getApplicationContext();
                Toast.makeText(context, context.getString(R.string.text_search_for_peers), Toast.LENGTH_LONG).show();
                networkManager.discoverPeers();
                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WifiDirectBroadcastReceiver(networkManager);
        registerReceiver(receiver, networkManager.getIntentFilter());
        networkManager.discoverPeers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkManager.stopPeerDiscovery();
        unregisterReceiver(receiver);
    }

    @Override
    public void onChannelDisconnected() {
        // todo
    }

    @Override
    public void onAction(WifiP2pDevice device, MyDeviceItemRecyclerViewAdapter.ViewHolder holder) {
        if (device.status == WifiP2pDevice.AVAILABLE) {
            connect(device);
        } else if (device.status == WifiP2pDevice.CONNECTED) {
            disconnect();
        } else if (device.status == WifiP2pDevice.INVITED) {
            cancelConnect();
        }
    }

    /**
     * Disconnect this device from group
     */
    @Override
    public void disconnect() {
        WifiP2pManager manager = networkManager.getManager();
        manager.removeGroup(networkManager.getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("CONNECT", "REMOVED");
            }

            @Override
            public void onFailure(int i) {
                Log.d("CONNECT", "FAILED");
            }
        });
        networkManager.discoverPeers();
    }

    /**
     * Connect this device to other device/group.
     * @param device target device
     */
    @Override
    public void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        WifiP2pManager manager = networkManager.getManager();
        manager.connect(networkManager.getChannel(), config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("CONNECT", "Success");
                refreshGroupInfo();
            }

            @Override
            public void onFailure(int i) {
                Log.d("CONNECT", "Failure");

            }
        });
    }

    @Override
    public void cancelConnect() {
        WifiP2pManager manager = networkManager.getManager();
        manager.cancelConnect(networkManager.getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("CANCEL CONNECT", "Success");
            }

            @Override
            public void onFailure(int i) {
                Log.d("CANCEL CONNECT", "Failure");
            }
        });
    }

    @Override
    public void cancelDisconnect() {
        WifiP2pManager manager = networkManager.getManager();
        if (manager != null) {
            // todo
        }
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        // request clients and refresh group-info
        CarcassonneApp.getNetworkController().isGroupOwner(info.isGroupOwner);
        refreshGroupInfo();
    }

    /**
     * Refresh the group status and update the network controller
     */
    private void refreshGroupInfo() {
        WifiP2pManager manager = networkManager.getManager();
        manager.requestGroupInfo(networkManager.getChannel(), new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                INetworkController networkController = CarcassonneApp.getNetworkController();
                Context context = getApplicationContext();

                TextView groupOwner = findViewById(R.id.text_group_owner);
                TextView playerCount = findViewById(R.id.text_player_count);

                if (wifiP2pGroup == null) {
                    networkController.reset();
                    groupOwner.setText(context.getString(R.string.text_no_group_formed));
                    playerCount.setText("");
                } else {
                    WifiP2pDevice owner = wifiP2pGroup.getOwner();
                    List<WifiP2pDevice> clients = new ArrayList<>(wifiP2pGroup.getClientList());

                    networkController.setClients(clients);
                    networkController.setGroupOwner(owner);
                    networkController.isGroupOwner(wifiP2pGroup.isGroupOwner());

                    groupOwner.setText(networkController.isGroupOwner()
                        ? context.getString(R.string.title_group_owner)
                        : context.getString(R.string.text_connected_to) + " " + owner.deviceName);

                    DeviceListFragment deviceList = (DeviceListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_devices);

                    if (wifiP2pGroup.isGroupOwner()) {
                        String text = context.getString(R.string.text_player_in_group) + ": " + (1 + clients.size());
                        playerCount.setText(text);

                        // populate fragment list with connected clients
//                        deviceList.addConnectedDevices(clients);
                    } else {
                        // populate fragment list with group owner
//                        deviceList.addConnectedDevice(owner);
                    }
                }
            }
        });
    }
}