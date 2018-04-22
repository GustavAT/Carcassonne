package distudios.at.carcassonne.gui.groups;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.nio.charset.IllegalCharsetNameException;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;
import distudios.at.carcassonne.networking.connection.DataCallback;
import distudios.at.carcassonne.networking.connection.DeviceCallback;

public class GroupOverview extends AppCompatActivity implements DeviceCallback.IDeviceCallback, DataCallback.IDataCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonAction = findViewById(R.id.button_cleanup_group);

        final INetworkController controller = CarcassonneApp.getNetworkController();
        if (controller.isClient()) {

            // code for client

            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.getNetwork().unregisterClient(false);
                    Log.d("Carcassonne", "Device unregistered!");

                    Intent i = new Intent(getApplicationContext(), Group2Activity.class);
                    startActivity(i);
                }
            });
            buttonAction.setText(R.string.text_leave_group);
        } else {

            // code for host

            DeviceCallback.callback = this;
            setupService();
            buttonAction.setText(R.string.text_cleanup);

            final Activity thisActivity = this;
            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    destroyService();
                    NavUtils.navigateUpFromSameTask(thisActivity);
                }
            });
        }


        DataCallback.callback = this;


        Button sendMessage = findViewById(R.id.button_sendTestMessage);
        final EditText testMessage = findViewById(R.id.editText_testMessage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.isHost()) {
                    controller.sendToAllDevices(testMessage.getText().toString());
                } else if (controller.isClient()) {
                    controller.sendToHost(testMessage.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                destroyService();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        destroyService();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void setupService() {
        Salut network = CarcassonneApp.getNetworkController().getNetwork();
        network.startNetworkService(new DeviceCallback());
    }

    private void destroyService() {
        Salut network = CarcassonneApp.getNetworkController().getNetwork();
        network.stopNetworkService(false);
    }

    @Override
    public void call(SalutDevice salutDevice) {
        Salut network = CarcassonneApp.getNetworkController().getNetwork();
        String text = "";
        for (SalutDevice d : network.registeredClients) {
            text += d.readableName + System.getProperty("line.separator");
        }

        text += network.thisDevice.readableName;
        ((TextView)findViewById(R.id.textView_deviceList)).setText(text);
    }

    @Override
    public void onDataReceived(Object data) {
        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
        INetworkController controller = CarcassonneApp.getNetworkController();
        if (controller.isHost()) {
            controller.sendToAllDevices(data);
        }
    }
}
