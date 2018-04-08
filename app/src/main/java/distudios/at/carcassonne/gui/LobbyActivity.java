package distudios.at.carcassonne.gui;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.NetworkManager;
import distudios.at.carcassonne.networking.OnDeviceChangedEventListener;
import distudios.at.carcassonne.networking.OnWifiP2pDeviceActionEventListener;
import distudios.at.carcassonne.networking.WifiDirectBroadcastReceiver;

public class LobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, OnWifiP2pDeviceActionEventListener {

    // network
    private WifiDirectBroadcastReceiver receiver;
    private NetworkManager networkManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    networkManager.discoverPeers();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_start:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        WifiP2pManager.PeerListListener devices = (WifiP2pManager.PeerListListener) getSupportFragmentManager().findFragmentById(R.id.fragment_devices);
        networkManager = new NetworkManager(this, devices);
        networkManager.setOnDeviceChangedEventListener(new OnDeviceChangedEventListener() {
            @Override
            public void onEvent(WifiP2pDevice device) {
                // set device name and host address
                TextView deviceName = findViewById(R.id.textPhoneName);
                deviceName.setText(device.deviceName);
                TextView hostAddress = findViewById(R.id.textHostAdress);
                hostAddress.setText(device.deviceAddress);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WifiDirectBroadcastReceiver(networkManager);
        registerReceiver(receiver, networkManager.getIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onChannelDisconnected() {
        // todo
    }

    @Override
    public void onAction(WifiP2pDevice device, MyDeviceItemRecyclerViewAdapter.ViewHolder holder) {
        if (device.status == WifiP2pDevice.AVAILABLE) {
            // connect

            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;
            config.wps.setup = WpsInfo.PBC;

            WifiP2pManager manager = networkManager.getManager();
            manager.connect(networkManager.getChannel(), config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d("CONNECT", "Success");
                }

                @Override
                public void onFailure(int i) {
                    Log.d("CONNECT", "Failure");

                }
            });

        } else if (device.status == WifiP2pDevice.CONNECTED) {
            // disconnect
        }
    }
}
