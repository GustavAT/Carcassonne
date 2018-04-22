package distudios.at.carcassonne.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.MainActivity;
import distudios.at.carcassonne.R;

public class Rules extends AppCompatActivity {

    String text_rules;
    TextView show_rules;
    TextView close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        close = (TextView) findViewById(R.id.txtclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Rules.this, MainActivity.class));
            }
        });

        show_rules=(TextView)findViewById(R.id.rulesView);
        try {

            InputStream rules= getAssets().open("rules.txt");
            int result = rules.available();
            byte[] bytes = new byte[result];
            rules.read(bytes);
            rules.close();
            text_rules = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        show_rules.setText(text_rules);
    }
}
