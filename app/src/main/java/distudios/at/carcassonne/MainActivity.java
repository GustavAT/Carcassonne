package distudios.at.carcassonne;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import distudios.at.carcassonne.networking.NetworkManager;
import distudios.at.carcassonne.networking.WifiDirectBroadcastReceiver;

public class    MainActivity extends AppCompatActivity {

    public NetworkManager manager;
    public WifiDirectBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        manager = new NetworkManager(this);

        Button discover = findViewById(R.id.discover_peers);
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.discoverPeers();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new WifiDirectBroadcastReceiver(manager);
        registerReceiver(receiver, manager.getIntentFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
