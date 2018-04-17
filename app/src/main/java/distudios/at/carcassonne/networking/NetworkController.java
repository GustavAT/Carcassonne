package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutServiceData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.networking.connection.DataCallback;

public class NetworkController implements INetworkController {

    public NetworkController() {
        clients = new ArrayList<>();
    }

    public Salut network;
    public SalutServiceData serviceData;
    public SalutDataReceiver dataReceiver;

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


    @Override
    public void init(Activity activity) {
        serviceData = new SalutServiceData("CarcassonneService", 60000, CarcassonneApp.playerName);
        dataReceiver = new SalutDataReceiver(activity, new DataCallback());
        network = new Salut(dataReceiver, serviceData, null);
    }

    public Salut getNetwork() {
        return network;
    }

    public boolean isConnected() {
        if (network == null) return false;

        return network.isConnectedToAnotherDevice || network.isRunningAsHost;
    }
}
