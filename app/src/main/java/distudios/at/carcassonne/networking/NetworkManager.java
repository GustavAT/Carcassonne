package distudios.at.carcassonne.networking;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import distudios.at.carcassonne.gui.LobbyActivity;

/**
 * Created by Andreas on 19.03.2018.
 */

public class NetworkManager {

    private Context context;
    private IntentFilter filter;

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;
    private WifiP2pManager.ActionListener actionListener;

    private WifiP2pDevice thisDevice;
    private OnDeviceChangedEventListener deviceChangedEventListener;

    public NetworkManager(Context context, WifiP2pManager.PeerListListener peerListListener) {

        this.context = context;
        this.filter = new IntentFilter();
        this.filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        this.filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        this.filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        this.filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(context, context.getMainLooper(), null);

        this.peerListListener = peerListListener;
        initConnectionInfoListener();
        initActionListener();
    }

    private void initConnectionInfoListener() {
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {

            }
        };
    }

    private void initActionListener() {
        actionListener = new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("WIFI", "Searching for peers...");
            }

            @Override
            public void onFailure(int i) {
                Log.d("WIFI", "Searching failed");
            }
        };
    }

    public WifiP2pManager getManager() {
        return manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    public IntentFilter getIntentFilter() {
        return filter;
    }

    public WifiP2pManager.PeerListListener getPeerListListener() {
        return peerListListener;
    }

    public WifiP2pManager.ConnectionInfoListener getConnectionInfoListener() {
        return connectionInfoListener;
    }

    public void discoverPeers() {
        manager.discoverPeers(channel, actionListener);
    }

    public void setOnDeviceChangedEventListener(OnDeviceChangedEventListener listener) {
        deviceChangedEventListener = listener;
    }

    public void setDevice(WifiP2pDevice device) {
        thisDevice = device;
        if (deviceChangedEventListener != null) {
            deviceChangedEventListener.onEvent(device);
        }
    }

    public WifiP2pDevice getDevice() {
        return thisDevice;
    }
}
