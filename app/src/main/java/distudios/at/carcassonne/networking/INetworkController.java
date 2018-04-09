package distudios.at.carcassonne.networking;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.List;

public interface INetworkController {

    void createConnection(boolean isGroupOwner);
    void sendData(Object data, int type);
    Object receiveData(int type);

    boolean isGroupOwner();
    void isGroupOwner(boolean isGroupOwner);

    List<WifiP2pDevice> getClients();
    WifiP2pDevice getGroupOwner();

    void setClients(List<WifiP2pDevice> clients);
    void setGroupOwner(WifiP2pDevice groupOwner);

    void reset();

    boolean canConnect();

}
