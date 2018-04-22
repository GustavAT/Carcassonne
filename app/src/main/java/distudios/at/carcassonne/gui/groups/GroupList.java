package distudios.at.carcassonne.gui.groups;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.util.ArrayList;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.connection.DataCallback;
import distudios.at.carcassonne.networking.connection.DiscoveryCallback;

public class GroupList extends AppCompatActivity implements DiscoveryCallback.IDiscoveryCallback, SwipeRefreshLayout.OnRefreshListener, DataCallback.IDataCallback, MySalutDeviceRecyclerViewAdapter.IDeviceConnected {

    private Salut network;
    private SalutDeviceFragment fragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        setRefreshing();

        fragment = (SalutDeviceFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bullshit);
        fragment.adapter.callback = this;

        DiscoveryCallback.callback = this;
        DataCallback.callback = this;

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
        network.discoverNetworkServices(new DiscoveryCallback(), true);
    }


    public Runnable refreshRunnable;
    private Handler refreshHandler;

    @Override
    public void call() {
        fragment.adapter.setDevices(network.foundDevices);

        swipeRefreshLayout.setRefreshing(false);
        if (refreshHandler != null) {
            refreshHandler.removeCallbacks(refreshRunnable);
        }
    }

    @Override
    public void onRefresh() {
        setupDiscovery();
        fragment.adapter.setDevices(new ArrayList<SalutDevice>());
        setRefreshing();
    }

    private void setRefreshing() {
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                Snackbar.make(getWindow().getDecorView().getRootView(), getString(R.string.text_no_new_lobbies_found), Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        refreshHandler = new Handler();
        refreshHandler.postDelayed(refreshRunnable, 10000);
    }

    @Override
    public void onDataReceived(Object data) {
        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnect() {
        Intent i = new Intent(getApplicationContext(), GroupOverview.class);
        startActivity(i);
    }
}
