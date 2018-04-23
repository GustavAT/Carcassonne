package distudios.at.carcassonne.gui.groups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Salut;

import java.nio.charset.IllegalCharsetNameException;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.gui.field.GameActivity;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.DataCallback;

public class Group2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group2);

        final Button testField = findViewById(R.id.button_testField);
        final Button create = findViewById(R.id.button_createGroup);
        final Button join = findViewById(R.id.button_joinGroup);
        final Button clear = findViewById(R.id.button_clear);
        final EditText editText = findViewById(R.id.editText_groupName);

        editText.setEnabled(!CarcassonneApp.playerName.isEmpty() ? false : true);

        editText.setText(CarcassonneApp.playerName);
        editText.clearFocus();
        create.setEnabled(!CarcassonneApp.playerName.isEmpty());
        join.setEnabled(!CarcassonneApp.playerName.isEmpty());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean emptyText = charSequence.toString().isEmpty();
                create.setEnabled(!emptyText);
                join.setEnabled(!emptyText);

                if (emptyText) {
                    editText.setError(getResources().getString(R.string.error_player_name));
                } else {
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        testField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ensureNetwork();
                clearNetwork();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarcassonneApp.playerName = editText.getText().toString();
                ensureNetwork();
                Intent i = new Intent(getApplicationContext(), GroupOverview.class);
                startActivity(i);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarcassonneApp.playerName = editText.getText().toString();
                ensureNetwork();
                Intent i = new Intent(getApplicationContext(), GroupList.class);
                startActivity(i);
            }
        });
    }


    private void ensureNetwork() {
        INetworkController controller = CarcassonneApp.getNetworkController();
        Salut network = controller.getNetwork();

        if (network == null) {
            controller.init(this);
        }
    }

    private void clearNetwork() {
        Salut network = CarcassonneApp.getNetworkController().getNetwork();

        if(!network.isRunningAsHost && !network.isDiscovering)
        {
            network.discoverNetworkServices(new SalutCallback() {
                @Override
                public void call() {
                }
            }, true);
        }
        else
        {
            network.stopServiceDiscovery(true);
        }
    }
}
