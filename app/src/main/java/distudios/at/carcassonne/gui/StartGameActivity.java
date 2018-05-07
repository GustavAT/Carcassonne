package distudios.at.carcassonne.gui;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.gui.field.GameFragment;
import distudios.at.carcassonne.gui.field.OnFragmentInteractionListener;
import distudios.at.carcassonne.gui.game.LogsFragment;
import distudios.at.carcassonne.gui.game.NextCard;
import distudios.at.carcassonne.gui.game.ScoreFragment;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;
import distudios.at.carcassonne.networking.connection.DataCallback;

public class StartGameActivity extends AppCompatActivity implements DataCallback.IDataCallback, OnFragmentInteractionListener {


    private GameFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);


        //Set up the viewPager with the selection adapter.
        ViewPager viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CarcassonneApp.getSoundController().playSound();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                CarcassonneApp.getSoundController().playSound();
            }
        });

        // setup data-callback
        DataCallback.callback = this;
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        gameFragment = GameFragment.newInstance("", "");

        adapter.addFragment(gameFragment,getResources().getString(R.string.app_name));
        adapter.addFragment(new ScoreFragment(),"Score");
        adapter.addFragment(new NextCard(),"Next Card");
        adapter.addFragment(new LogsFragment(),"Logs");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDataReceived(CarcassonneMessage message) {

        switch (message.type) {
            case 0:

                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // do something useful
    }
}
