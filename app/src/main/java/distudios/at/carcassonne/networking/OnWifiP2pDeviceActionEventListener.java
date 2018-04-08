package distudios.at.carcassonne.networking;

import android.net.wifi.p2p.WifiP2pDevice;
import android.widget.Button;

import distudios.at.carcassonne.gui.MyDeviceItemRecyclerViewAdapter;

public interface OnWifiP2pDeviceActionEventListener {
    void onAction(WifiP2pDevice device, MyDeviceItemRecyclerViewAdapter.ViewHolder holder);
}
