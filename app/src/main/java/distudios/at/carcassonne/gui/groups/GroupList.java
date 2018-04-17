package distudios.at.carcassonne.gui.groups;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.gui.lobby.SalutDeviceFragment;
import distudios.at.carcassonne.networking.INetworkController;

public class GroupList extends AppCompatActivity implements SalutCallback {

    private Salut network;
    private BullshitFragment fragment;
//    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

//        fragment = (BullshitFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        fragment = new BullshitFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transa = manager.beginTransaction();
        transa.replace(R.id.frameLayout, fragment);
        transa.commit();

        setupDiscovery();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                stopDiscovery();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        stopDiscovery();
        finish();
    }

    private void setupDiscovery() {
        network = CarcassonneApp.getNetworkController().getNetwork();

        stopDiscovery();
        startDiscovery();
    }

    private void stopDiscovery() {
        if (network.isDiscovering) {
            network.stopServiceDiscovery(false);
        }
    }

    private void startDiscovery() {
        network.discoverNetworkServices(this, true);
    }

    @Override
    public void call() {
        Log.d("CALL", "found " + network.foundDevices.size());
        fragment.adapter.fill();
//        swipeRefreshLayout.setRefreshing(false);

        Log.d("CALL", "RECEIVED " + network.foundDevices.size());
//
//        if (refreshHandler != null) {
//            refreshHandler.removeCallbacks(refreshRunnable);
//        }
    }

//    private Runnable refreshRunnable;
//    private Handler refreshHandler;
//
//    @Override
//    public void onRefresh() {
//        setupDiscovery();
//        fragment.adapter.clear();
//
//        refreshRunnable = new Runnable() {
//            @Override
//            public void run() {
//                Snackbar.make(getWindow().getDecorView().getRootView(), getString(R.string.text_no_new_lobbies_found), Snackbar.LENGTH_LONG).show();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        };
//
//        refreshHandler = new Handler();
//        refreshHandler.postDelayed(refreshRunnable, 10000);
//    }
//
//    swipeContainer = (SwipeRefreshLayout) view;
//            swipeContainer.setOnRefreshListener(this);
//            swipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
}
