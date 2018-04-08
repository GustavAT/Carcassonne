package distudios.at.carcassonne;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import distudios.at.carcassonne.networking.NetworkManager;
import distudios.at.carcassonne.networking.WifiDirectBroadcastReceiver;

public class    MainActivity extends AppCompatActivity {

    public NetworkManager manager;
    public WifiDirectBroadcastReceiver receiver;
    protected CarcassonneAppClass app;


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
        registerComponentCallbacks(app);
        app=(CarcassonneAppClass)getApplication();
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
