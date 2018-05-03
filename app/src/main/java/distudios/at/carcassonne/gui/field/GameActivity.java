package distudios.at.carcassonne.gui.field;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;
import distudios.at.carcassonne.networking.connection.DataCallback;

public class GameActivity extends AppCompatActivity implements OnFragmentInteractionListener, DataCallback.IDataCallback {

    private Fragment currentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game:
                    currentFragment = GameFragment.newInstance("", "");
                    replaceCurrentFragment();
                    return true;
                case R.id.navigation_score:
                    currentFragment = ScoreFragment.newInstance("", "");
                    replaceCurrentFragment();
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        currentFragment = GameFragment.newInstance("", "");
        replaceCurrentFragment();

        DataCallback.callback = this;
    }

    private void replaceCurrentFragment() {
        FragmentTransaction transa = getSupportFragmentManager().beginTransaction();
        transa.replace(R.id.frame_container, currentFragment);
        transa.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("Carcassonne", "Message from inner fragment");
    }

    // central entry point for all incomming messages
    @Override
    public void onDataReceived(CarcassonneMessage message) {
        INetworkController controller = CarcassonneApp.getNetworkController();
        switch (message.type) {
            case CarcassonneMessage.GAME_STATE_UPDATE:
                // new gamestate received, update my gamestate
                CarcassonneApp.getGameController().setState(message.state);
                if (currentFragment instanceof GameFragment) {
                    ((GameFragment)currentFragment).updatePlayField();
                }

                if (controller.isHost()) {
                    controller.sendToAllDevices(message);
                }

                Toast.makeText(getApplicationContext(), "Game state update received", Toast.LENGTH_SHORT).show();
                break;
            case CarcassonneMessage.END_TURN:
                CarcassonneApp.getGameController().setState(message.state);

                if (currentFragment instanceof GameFragment) {
                    ((GameFragment)currentFragment).updatePlayField();
                }

                if (controller.isHost()) {
                    controller.sendToAllDevices(message);
                }


                String msg = "Its " + message.state.currentPlayer + "'s turn";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
