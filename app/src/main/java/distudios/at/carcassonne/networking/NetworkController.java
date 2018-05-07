package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.graphics.Color;
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
import distudios.at.carcassonne.networking.connection.PlayerInfo;

public class NetworkController implements INetworkController {

    public Salut network;
    public SalutServiceData serviceData;
    public SalutDataReceiver dataReceiver;
    public Map<String, PlayerInfo> playerMappings;

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
    public void sendMessage(CarcassonneMessage message) {
        if (isHost()) {
            sendToAllDevices(message);
        } else if (isClient()) {
            sendToHost(message);
        }
    }

    @Override
    public boolean isHost() {
        return network != null && network.isConnectedToAnotherDevice && network.isRunningAsHost;
    }

    @Override
    public boolean isClient() {
        return network != null && network.isConnectedToAnotherDevice;
    }

    /***
     * Create mappings between salud devices (device mac address) and player numbers (1-5)
     * Important: call this method before initializing the game
     */
    @Override
    public Map<String, PlayerInfo> createPlayerMappings() {
        Map<String, PlayerInfo> mappings = new HashMap<>();

        mappings.put(network.thisDevice.instanceName, createPlayerInfo(network.thisDevice, 0));

        int playerNumber = 1;
        for (SalutDevice sd : network.registeredClients) {
            mappings.put(sd.instanceName, createPlayerInfo(sd, playerNumber));
            playerNumber++;
        }

        playerMappings = mappings;
        return mappings;
    }

    private PlayerInfo createPlayerInfo(SalutDevice device, int number) {
        PlayerInfo info = new PlayerInfo();
        info.deviceName = device.deviceName;
        info.playerNumber = number;
        info.instanceName = device.instanceName;
        info.color = Color.RED;
        return info;
    }

    @Override
    public Map<String, PlayerInfo> getPlayerMappings() {
        return playerMappings;
    }

    @Override
    public void setPlayerMappings(Map<String, PlayerInfo> mappings) {
        playerMappings = mappings;
    }

    @Override
    public int getDevicePlayerNumber() {
        String thisDevice = network.thisDevice.instanceName;
        return playerMappings.containsKey(thisDevice) ? playerMappings.get(thisDevice).playerNumber : -1;
    }

    @Override
    public int getDeviceCount() {
        if (!isHost()) return -1;

        return 1 + network.registeredClients.size();
    }
}
