package distudios.at.carcassonne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import distudios.at.carcassonne.gui.Rules;
import distudios.at.carcassonne.gui.Settings;

public class MainActivity extends AppCompatActivity {

    Button btnSettings;
    Button btnRules;
    CarcassonneApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSettings = findViewById(R.id.btnSettings);
        btnRules = findViewById(R.id.btnShowRules);
        app = (CarcassonneApp)getApplication();


        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
                app.soundPool.play(1,1,1,0,0,1);
            }
        });

        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Rules.class));
                app.soundPool.play(1,1,1,0,0,1);
            }
        });

    }
}
