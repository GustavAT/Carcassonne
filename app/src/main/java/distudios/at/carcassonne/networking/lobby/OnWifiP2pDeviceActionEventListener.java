package distudios.at.carcassonne.networking.lobby;

import android.net.wifi.p2p.WifiP2pDevice;

public interface OnWifiP2pDeviceActionEventListener {
    void onAction(WifiP2pDevice device, MyDeviceItemRecyclerViewAdapter.ViewHolder holder);
}
