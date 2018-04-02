package distudios.at.carcassonne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by Simon on 02.04.2018.
 * Placeholder view for setting up a multiplayer game
 */

public class SetGameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgame);

        Button neuesSpiel = findViewById(R.id.new_game);
        neuesSpiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStartGameClicked();
            }
        });
    }

    private void btnStartGameClicked(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
