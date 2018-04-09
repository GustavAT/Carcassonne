package distudios.at.carcassonne.networking.lobby;

import android.net.wifi.p2p.WifiP2pDevice;

public interface DeviceActionListener {

    void cancelDisconnect();
    void connect(WifiP2pDevice device);
    void disconnect();
    void cancelConnect();

}
