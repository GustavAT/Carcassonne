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

import distudios.at.carcassonne.R;

public class GameActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private FrameLayout frameContainer;
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

        frameContainer = findViewById(R.id.frame_container);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        currentFragment = GameFragment.newInstance("", "");
        replaceCurrentFragment();
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
}
