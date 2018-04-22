package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;

import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;

import java.util.List;

import distudios.at.carcassonne.networking.connection.DataCallback;

public interface INetworkController {

    void init(Activity activity);
    Salut getNetwork();
    void sendToAllDevices(Object data);
    void sendToHost(Object data);

    boolean isHost();
    boolean isClient();

}
