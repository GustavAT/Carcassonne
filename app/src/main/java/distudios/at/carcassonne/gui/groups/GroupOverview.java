package distudios.at.carcassonne.gui.groups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.util.Map;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.gui.field.GameActivity;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;
import distudios.at.carcassonne.networking.connection.DataCallback;
import distudios.at.carcassonne.networking.connection.DeviceCallback;
import distudios.at.carcassonne.networking.connection.PlayerInfo;

public class GroupOverview extends AppCompatActivity implements DeviceCallback.IDeviceCallback, DataCallback.IDataCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonStartGame = findViewById(R.id.button_startGame);
        Button buttonAction = findViewById(R.id.button_cleanup_group);

        final INetworkController controller = CarcassonneApp.getNetworkController();
        if (controller.isClient()) {

            // code for client

            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.getNetwork().unregisterClient(false);
                    Intent i = new Intent(getApplicationContext(), Group2Activity.class);
                    startActivity(i);
                }
            });
            buttonAction.setText(R.string.text_leave_group);

            buttonStartGame.setVisibility(View.GONE);
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

            buttonStartGame.setVisibility(View.VISIBLE);
            buttonStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // tell other devices to start the game and start the game activity
                    CarcassonneApp.getGameController().startGame();
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(i);
                }
            });
        }


        DataCallback.callback = this;


        Button sendMessage = findViewById(R.id.button_sendTestMessage);
        final EditText testMessage = findViewById(R.id.editText_testMessage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CarcassonneMessage message = new CarcassonneMessage(CarcassonneMessage.DUMMY_MESSAGE);
                message.other = testMessage.getText().toString();
                controller.sendMessage(message);
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
        INetworkController controller = CarcassonneApp.getNetworkController();
        CarcassonneMessage message =new CarcassonneMessage(CarcassonneMessage.PLAYER_JOIN);
        message.playerMappings = controller.createPlayerMappings();
        controller.sendToAllDevices(message);
        showPlayers();
    }

    @Override
    public void onDataReceived(CarcassonneMessage data) {

        INetworkController controller = CarcassonneApp.getNetworkController();

        switch (data.type) {
            case CarcassonneMessage.DUMMY_MESSAGE:
                // dummy message received. if this device is host, broadcast to all devices
                Toast.makeText(getApplicationContext(), data.other, Toast.LENGTH_SHORT).show();
                if (controller.isHost()) {
                    controller.sendToAllDevices(data);
                }
                break;
            case CarcassonneMessage.HOST_START_GAME:
                controller.setPlayerMappings(data.playerMappings);
                CarcassonneApp.getGameController().setState(data.state);
                Toast.makeText(getApplicationContext(), "Player mappings received", Toast.LENGTH_SHORT).show();

                // initial state received from host, start game and wait
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);

                break;
            case CarcassonneMessage.PLAYER_JOIN:
                controller.setPlayerMappings(data.playerMappings);
                Toast.makeText(getApplicationContext(), "Players changed", Toast.LENGTH_SHORT).show();

                showPlayers();

                break;
        }
    }

    private void showPlayers() {
        INetworkController controller = CarcassonneApp.getNetworkController();
        Map<String, PlayerInfo> mappings = controller.getPlayerMappings();

        String text = "";
        for (PlayerInfo info : mappings.values()) {
            text += info.deviceName + System.getProperty("line.separator");
        }

        ((TextView)findViewById(R.id.textView_deviceList)).setText(text);
    }
}
