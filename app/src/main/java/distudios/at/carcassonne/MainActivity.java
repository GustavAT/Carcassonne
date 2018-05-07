package distudios.at.carcassonne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import distudios.at.carcassonne.gui.ISoundController;
import distudios.at.carcassonne.gui.Rules;
import distudios.at.carcassonne.gui.Settings;

public class MainActivity extends AppCompatActivity {

    private Button btnSettings;
    private Button btnRules;
    ISoundController soundController;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        btnSettings = findViewById(R.id.btnSettings);
//        btnRules = findViewById(R.id.btnShowRules);
//        soundController= CarcassonneApp.getSoundController();
//
//
//
//        btnSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Settings.class));
//                soundController.playSound();
//            }
//        });
//
//        btnRules.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Rules.class));
//                soundController.playSound();
//            }
//        });
//
//    }
}
