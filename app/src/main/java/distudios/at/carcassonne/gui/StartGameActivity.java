package distudios.at.carcassonne.gui;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.gui.Fragments.LogsFragment;
import distudios.at.carcassonne.gui.Fragments.NextCard;
import distudios.at.carcassonne.gui.Fragments.ScoreFragment;
import distudios.at.carcassonne.gui.Fragments.StartGameFragment;

public class StartGameActivity extends AppCompatActivity {


    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);


        sectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
        //Set up the viewPager with the selection adapter.
        viewPager= findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartGameFragment(),"Board");
        adapter.addFragment(new ScoreFragment(),"Score");
        adapter.addFragment(new NextCard(),"Next Card");
        adapter.addFragment(new LogsFragment(),"Logs");
        viewPager.setAdapter(adapter);

    }
}
