package distudios.at.carcassonne.gui.lobby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.INetworkController;

public class Main2Activity extends AppCompatActivity implements SalutDataCallback {

    private Salut network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        INetworkController controller = CarcassonneApp.getNetworkController();
//        controller.init(this, "",this);
        network = controller.getNetwork();

        findViewById(R.id.buttonDiscover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!network.isDiscovering) {
                    network.discoverNetworkServices(new SalutCallback() {
                        @Override
                        public void call() {
                            Log.d("SALUT", "found");
                        }
                    }, true);
                } else {
                    network.stopServiceDiscovery(true);
                }
            }
        });

        findViewById(R.id.buttonGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!network.isRunningAsHost) {
                    network.startNetworkService(new SalutDeviceCallback() {
                        @Override
                        public void call(SalutDevice salutDevice) {
                            Log.d("SALUT", "connected" + salutDevice.deviceName);
                        }
                    });
                } else {
                    network.stopNetworkService(false);
                }
            }
        });
    }

    @Override
    public void onDataReceived(Object o) {

    }
}
