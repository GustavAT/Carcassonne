package distudios.at.carcassonne.gui.lobby;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Callbacks.SalutServiceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.INetworkController;

public class GroupActivity extends AppCompatActivity implements SalutDataCallback, SalutCallback, CreateGroupDialog.OnCreateGroupEvents {

    private TextView mTextMessage;
    private SalutDeviceFragment deviceFragment;
    private GroupOverviewFragment groupOverviewFragment;
    private Salut network;
    private GroupActivity thisActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transa = manager.beginTransaction();

            stopDiscovering();

            switch (item.getItemId()) {
                case R.id.navigation_group:
                    mTextMessage.setText(R.string.title_home);
//
                    transa.replace(R.id.frame_container, groupOverviewFragment);
                    transa.commit();

                    return true;
                case R.id.navigation_browser:
                    mTextMessage.setText(R.string.title_dashboard);

                    transa.replace(R.id.frame_container, deviceFragment);
                    transa.commit();

                    discoverServices();

                    return true;
                case R.id.navigation_game:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        deviceFragment = new SalutDeviceFragment();
        groupOverviewFragment = new GroupOverviewFragment();

        thisActivity = this;

        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!network.isRunningAsHost) {
                    network.startNetworkService(new SalutDeviceCallback() {
                        @Override
                        public void call(SalutDevice salutDevice) {
                            // todo transa #3
                        }
                    });
                    mTextMessage.setText("Group created");
                } else {
                    network.stopNetworkService(false);
                    mTextMessage.setText("Group closed");
                }
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transa = manager.beginTransaction();
        transa.replace(R.id.frame_container, groupOverviewFragment);
        transa.commit();

        CreateGroupDialog dialog = new CreateGroupDialog();
        dialog.listener = this;
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onDataReceived(Object o) {
        // todo transa
    }

    @Override
    public void call() {
        deviceFragment.adapter.setDevices(network.foundDevices);
        if (deviceFragment.swipeContainer != null) {
            deviceFragment.swipeContainer.setRefreshing(false);
        }
    }

    public void discoverServices() {
        stopDiscovering();
        network.discoverNetworkServices(this, true);
    }

    public void stopDiscovering() {
        if (network.isDiscovering) {
            network.stopServiceDiscovery(false);
        }
    }

    @Override
    public void onCreateGroup(String groupName) {
        INetworkController controller = CarcassonneApp.getNetworkController();
//        controller.init(this, groupName,this);
        network = controller.getNetwork();
    }
}
