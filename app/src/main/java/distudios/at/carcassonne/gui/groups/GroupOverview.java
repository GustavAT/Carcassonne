package distudios.at.carcassonne.gui.groups;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;

public class GroupOverview extends AppCompatActivity implements SalutDeviceCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupService();
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
        network.startNetworkService(this);
    }

    private void destroyService() {
        Salut network = CarcassonneApp.getNetworkController().getNetwork();
        network.stopNetworkService(false);
    }

    @Override
    public void call(SalutDevice salutDevice) {
        // todo: add salut device to list
    }
}
