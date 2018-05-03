package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutDevice;
import com.peak.salut.SalutServiceData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;
import distudios.at.carcassonne.networking.connection.DataCallback;

public class NetworkController implements INetworkController {

    public Salut network;
    public SalutServiceData serviceData;
    public SalutDataReceiver dataReceiver;
    public Map<String, Integer> playerMappings;

    @Override
    public void init(Activity activity) {
        serviceData = new SalutServiceData("CarcassonneService", 60000, CarcassonneApp.playerName);
        dataReceiver = new SalutDataReceiver(activity, new DataCallback());
        network = new Salut(dataReceiver, serviceData, null);
        playerMappings = new HashMap<>();
    }

    public Salut getNetwork() {
        return network;
    }

    public boolean isConnected() {
        if (network == null) return false;

        return network.isConnectedToAnotherDevice || network.isRunningAsHost;
    }

    @Override
    public void sendToAllDevices(CarcassonneMessage data) {
        network.sendToAllDevices(data, new SalutCallback() {
            @Override
            public void call() {
                Log.e("Carcassonne", "Sending data failed");
            }
        });
    }

    @Override
    public void sendToHost(CarcassonneMessage data) {
        network.sendToHost(data, new SalutCallback() {
            @Override
            public void call() {
                Log.e("Carcassonne", "Sending data failed");
            }
        });
    }

    @Override
    public boolean isHost() {
        return network.isConnectedToAnotherDevice && network.isRunningAsHost;
    }

    @Override
    public boolean isClient() {
        return network.isConnectedToAnotherDevice;
    }

    /***
     * Create mappings between salud devices (device mac address) and player numbers (1-5)
     * Important: call this method before initializing the game
     */
    @Override
    public void createPlayerMappings() {
        Map<String, Integer> mappings = new HashMap<>();
        // todo check if instancename is correct
        mappings.put(network.thisDevice.instanceName, 0);

        int playerNumber = 1;
        for (SalutDevice sd : network.registeredClients) {
            mappings.put(sd.instanceName, playerNumber++);
        }

        playerMappings = mappings;
    }

    @Override
    public Map<String, Integer> getPlayerMappings() {
        return playerMappings;
    }

    @Override
    public void setPlayerMappings(Map<String, Integer> mappings) {
        playerMappings = mappings;
    }

    @Override
    public int getDevicePlayerNumber() {
        String thisDevice = network.thisDevice.instanceName;
        return playerMappings.containsKey(thisDevice) ? playerMappings.get(thisDevice) : -1;
    }

    @Override
    public int getDeviceCount() {
        if (!isHost()) return -1;

        return 1 + network.registeredClients.size();
    }
}
