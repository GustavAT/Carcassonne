package distudios.at.carcassonne.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import distudios.at.carcassonne.R;

public class Rules extends AppCompatActivity {

    TextView textViewRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewRules = findViewById(R.id.textView_rules);
        try {

            InputStream rules = getAssets().open("rules.txt");
            int result = rules.available();
            byte[] bytes = new byte[result];
            rules.read(bytes);
            rules.close();
            textViewRules.setText(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}