package distudios.at.carcassonne.gui.field;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.PlayerInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: ugly as fuck -> use recycle view
    private TextView textViewScore1;
    private TextView textViewScore2;
    private TextView textViewScore3;
    private TextView textViewScore4;
    private TextView textViewScore5;

    private OnFragmentInteractionListener mListener;


    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        INetworkController controller = CarcassonneApp.getNetworkController();
        Map<String, PlayerInfo> mappings = controller.getPlayerMappings();


        textViewScore1 = view.findViewById(R.id.text_score1);
        textViewScore2 = view.findViewById(R.id.text_score2);
        textViewScore3 = view.findViewById(R.id.text_score3);
        textViewScore4 = view.findViewById(R.id.text_score4);
        textViewScore5 = view.findViewById(R.id.text_score5);

        textViewScore1.setVisibility(View.GONE);
        textViewScore2.setVisibility(View.GONE);
        textViewScore3.setVisibility(View.GONE);
        textViewScore4.setVisibility(View.GONE);
        textViewScore5.setVisibility(View.GONE);

        GameState state = CarcassonneApp.getGameController().getGameState();
        // hint: mir geht alles am arsch! Code soll nur gehen, net guat ausschauen :(
        for (PlayerInfo info : mappings.values()) {

            String text = info.deviceName + ": " + state.getPoints(info.playerNumber);

            if (info.playerNumber == 0) {
                textViewScore1.setVisibility(View.VISIBLE);
                textViewScore1.setText(text);
            } else if (info.playerNumber == 1) {
                textViewScore2.setVisibility(View.VISIBLE);
                textViewScore2.setText(text);
            } else if (info.playerNumber == 2) {
                textViewScore3.setVisibility(View.VISIBLE);
                textViewScore3.setText(text);
            } else if (info.playerNumber == 3) {
                textViewScore4.setVisibility(View.VISIBLE);
                textViewScore4.setText(text);
            } else if (info.playerNumber == 4) {
                textViewScore5.setVisibility(View.VISIBLE);
                textViewScore5.setText(text);
            }
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
