package distudios.at.carcassonne.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by Andreas on 19.03.2018.
 */

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private NetworkManager networkManager;

    public WifiDirectBroadcastReceiver(NetworkManager manager) {
        networkManager = manager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            stateChanged(intent);
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            peersChanged();
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            connectionChanged(intent);
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            thisDeviceChanged(intent);
        }
    }

    private void stateChanged(Intent intent) {
        int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
        if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
            // Wifi Direct mode is enabled
            Log.d("WIFI", "wifi enabled");
        } else {
            Log.d("WIFI", "wifi disabled");
        }
    }

    private void peersChanged() {
        WifiP2pManager manager = networkManager.getManager();

        if (manager != null) {
            manager.requestPeers(networkManager.getChannel(), networkManager.getPeerListListener());
        }
    }

    private void connectionChanged(Intent intent) {
        WifiP2pManager manager = networkManager.getManager();

        if (manager == null) {
            return;
        }

        NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

        if (networkInfo.isConnected()) {

            // we are connected with the other device, request connection
            // info to find group owner IP
            manager.requestConnectionInfo(networkManager.getChannel(), networkManager.getConnectionInfoListener());
            Log.d("WIFI", "connected");
        } else {
            // It's a disconnect
            Log.d("WIFI", "disconnected");
        }
    }

    private void thisDeviceChanged(Intent intent) {
        WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
        networkManager.setDevice(device);
    }
}
