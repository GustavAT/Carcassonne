package distudios.at.carcassonne.networking;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;

import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import distudios.at.carcassonne.networking.connection.CarcassonneMessage;
import distudios.at.carcassonne.networking.connection.DataCallback;

public interface INetworkController {

    void init(Activity activity);
    Salut getNetwork();
    void sendToAllDevices(CarcassonneMessage data);
    void sendToHost(CarcassonneMessage data);

    boolean isHost();
    boolean isClient();

    void createPlayerMappings();
    Map<String, Integer> getPlayerMappings();
    void setPlayerMappings(Map<String, Integer> mappings);

}
