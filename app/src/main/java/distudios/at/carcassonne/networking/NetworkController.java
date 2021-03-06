package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutDevice;
import com.peak.salut.SalutServiceData;

import java.util.HashMap;
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
        serviceData = new SalutServiceData("CarcassonneService", 60000, CarcassonneApp.getPlayerName());
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
        return network != null && network.isConnectedToAnotherDevice && !network.isRunningAsHost;
    }

    /***
     * Create mappings between salud devices (device mac address) and player numbers (1-5)
     * Important: call this method before initializing the game
     */
    @Override
    public Map<String, PlayerInfo> createPlayerMappings() {
        Map<String, PlayerInfo> mappings = new HashMap<>();

        mappings.put(network.thisDevice.readableName + "_" + network.thisDevice.instanceName, createPlayerInfo(network.thisDevice, 0));

        int playerNumber = 1;
        for (SalutDevice sd : network.registeredClients) {
            mappings.put(sd.readableName + "_" + sd.instanceName, createPlayerInfo(sd, playerNumber));
            playerNumber++;
        }

        playerMappings = mappings;
        return mappings;
    }

    private PlayerInfo createPlayerInfo(SalutDevice device, int number) {
        PlayerInfo info = new PlayerInfo();
        info.raceType = 1;
        info.deviceName = device.readableName;
        info.playerNumber = number;
        info.instanceName = device.instanceName;
        if (number == 0) {
            info.color = Color.parseColor("#F6D612");
        } else if (number == 1) {
            info.color = Color.parseColor("#E3085D");
        } else if (number == 2) {
            info.color = Color.parseColor("#00FFAB");
        } else if (number == 3) {
            info.color = Color.parseColor("#7C2494");
        } else {
            info.color = Color.parseColor("#6F8BA9");
        }
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
        String thisDevice = network.thisDevice.readableName + "_" + network.thisDevice.instanceName;
        return playerMappings.containsKey(thisDevice) ? playerMappings.get(thisDevice).playerNumber : -1;
    }

    @Override
    public PlayerInfo getPlayerInfo(int id) {
        PlayerInfo info = null;
        for (PlayerInfo pi : playerMappings.values()) {
            if (pi.playerNumber == id) {
                info = pi;
                break;
            }
        }
        return info;
    }

    @Override
    public int getDeviceCount() {
        if (!isHost()) return -1;

        return 1 + network.registeredClients.size();
    }
}
