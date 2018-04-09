package distudios.at.carcassonne.networking;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.ArrayList;
import java.util.List;

public class NetworkController implements INetworkController {

    public NetworkController() {
        clients = new ArrayList<>();
    }

    private boolean isGroupOwner;
    public WifiP2pDevice groupOwner;
    public List<WifiP2pDevice> clients;

    public WifiP2pDevice getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(WifiP2pDevice groupOwner) {
        this.groupOwner = groupOwner;
    }

    @Override
    public void reset() {
        isGroupOwner = false;
        clients.clear();
        groupOwner = null;
    }

    @Override
    public boolean canConnect() {
        if (isGroupOwner) return false;

        if (groupOwner != null) return false;
        return true;
    }

    public List<WifiP2pDevice> getClients() {
        return clients;
    }

    public void setClients(List<WifiP2pDevice> clients) {
        this.clients = clients;
    }


    @Override
    public void createConnection(boolean isGroupOwner) {

    }

    @Override
    public void sendData(Object data, int type) {

    }

    @Override
    public Object receiveData(int type) {
        return null;
    }

    @Override
    public boolean isGroupOwner() {
        return isGroupOwner;
    }

    public void isGroupOwner(boolean isGroupOwner) {
        this.isGroupOwner = isGroupOwner;
    }


}
