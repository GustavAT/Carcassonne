package distudios.at.carcassonne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    Switch music_switch;
    TextView close;
    protected CarcassonneAppClass app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        registerComponentCallbacks(app);

        close = (TextView) findViewById(R.id.txtclose);
        music_switch = (Switch) findViewById(R.id.switchmusic);
        app = (CarcassonneAppClass) getApplication();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, MainActivity.class));
            }
        });


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

    }
}

