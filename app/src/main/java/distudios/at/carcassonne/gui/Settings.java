package distudios.at.carcassonne.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.MainActivity;
import distudios.at.carcassonne.R;

public class Settings extends AppCompatActivity {
    Switch music_switch;
    Switch sound_switch;
    TextView close;
    protected CarcassonneApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        registerComponentCallbacks(app);

        close = (TextView) findViewById(R.id.txtclose);
        music_switch = (Switch) findViewById(R.id.switchmusic);
        sound_switch = (Switch) findViewById(R.id.switchsound);
        app = (CarcassonneApp) getApplication();


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, MainActivity.class));
                app.soundPool.play(1,1,1,0,0,1);
            }
        });


        //Background music switch
        if (app.getBackground_music_state()) {
            music_switch.setChecked(true);
        } else {
            music_switch.setChecked(false);
        }

        music_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    app.setBackground_music_state(true);
                    app.startBackground_music();

                }else{
                    app.setBackground_music_state(false);
                    app.stopBackground_music();
                }
            }
        });

        //Sounds switch

        if (app.getSound_state()) {
            sound_switch.setChecked(true);
        } else {
            sound_switch.setChecked(false);
        }

        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    app.setSound_state(true);
                    app.bildSound();
                    app.loadSound();
                    app.soundPool.play(1,1,1,0,0,1);

                }else{
                    app.setSound_state(false);
                    app.soundPool.release();
                }
            }
        });


    }
}

