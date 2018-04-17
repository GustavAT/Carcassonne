package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;

import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;

import java.util.List;

import distudios.at.carcassonne.networking.connection.DataCallback;

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

    // todo: delete methods above

    void init(Activity activity);
    Salut getNetwork();
    boolean isConnected();

}
