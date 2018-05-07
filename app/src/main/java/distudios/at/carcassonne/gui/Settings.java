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
import distudios.at.carcassonne.gui.ISoundController;

public class Settings extends AppCompatActivity {
    private Switch music_switch;
    private Switch sound_switch;
    private TextView close;
    ISoundController soundController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        close = (TextView) findViewById(R.id.txtclose);
        music_switch = (Switch) findViewById(R.id.switchmusic);
        sound_switch = (Switch) findViewById(R.id.switchsound);





        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, MainActivity.class));
                soundController.playSound();
            }
        });

        soundController=CarcassonneApp.getSoundController();

        //Background music switch
        if (soundController.getBackground_music_state()) {
            music_switch.setChecked(true);
        } else {
            music_switch.setChecked(false);
        }

        music_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked) {
                    soundController.setBackground_music_state(true);
                    soundController.startBackground_music();
                }else{
                    soundController.setBackground_music_state(false);
                    soundController.stopBackground_music();
                }
            }
        });


        //Sounds switch

       if (soundController.getSound_state()) {
            sound_switch.setChecked(true);
        } else {
            sound_switch.setChecked(false);
        }

        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    soundController.setSound_state(true);
                    soundController.bildSound();
                    soundController.loadSound();
                    soundController.playSound();

                }else{
                    soundController.setSound_state(false);
                    soundController.stopSound();
                }
            }
        });


    }
}

