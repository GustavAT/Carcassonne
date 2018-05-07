package distudios.at.carcassonne.gui.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.gui.ScoreViewAdapter;

public class ScoreFragment extends Fragment {
    View view;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score,container,false);
        String[] players ={"Player 1","Player 2", "Player 3","Player 4","Player 5"};
//        listView= view.findViewById(R.id.score_list);


        ScoreViewAdapter arrayAdapter = new ScoreViewAdapter(getActivity().getApplicationContext(),players);
        listView.setAdapter(arrayAdapter);


        return view;

    }
}
