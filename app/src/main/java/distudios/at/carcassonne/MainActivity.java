package distudios.at.carcassonne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import distudios.at.carcassonne.gui.Rules;
import distudios.at.carcassonne.gui.Settings;
import distudios.at.carcassonne.networking.lobby.NetworkManager;
import distudios.at.carcassonne.networking.lobby.WifiDirectBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    public NetworkManager manager;
    public WifiDirectBroadcastReceiver receiver;
    protected CarcassonneApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerComponentCallbacks(app);
        app=(CarcassonneApp)getApplication();

//        manager = new NetworkManager(this);

        Button discover = findViewById(R.id.discover_peers);
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.discoverPeers();
            }
        });

        Button btnSettings = (Button) findViewById(R.id.btnSettings);
        Button btnRegeln =(Button)findViewById(R.id.btnShowRules);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });

        btnRegeln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Rules.class));
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
