package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.peak.salut.Callbacks.SalutCallback;
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

    public Salut network;
    public SalutServiceData serviceData;
    public SalutDataReceiver dataReceiver;

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

    @Override
    public void sendToAllDevices(Object data) {
        network.sendToAllDevices(data, new SalutCallback() {
            @Override
            public void call() {
                Log.e("Carcassonne", "Sending data failed");
            }
        });
    }

    @Override
    public void sendToHost(Object data) {
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
}
