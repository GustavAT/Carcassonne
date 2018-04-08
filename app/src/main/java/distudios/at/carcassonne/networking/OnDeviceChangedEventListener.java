package distudios.at.carcassonne.networking;

import android.net.wifi.p2p.WifiP2pDevice;

/**
 * Custom event when this device changes.
 */
public interface OnDeviceChangedEventListener {
    void onEvent(WifiP2pDevice device);
}
