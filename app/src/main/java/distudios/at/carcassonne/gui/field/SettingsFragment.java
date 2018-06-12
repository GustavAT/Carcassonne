package distudios.at.carcassonne.gui.field;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.peak.salut.Salut;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.engine.misc.ISoundController;
import distudios.at.carcassonne.gui.groups.Group2Activity;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    private Button buttonMainMenu;
    private Switch switchMusic;
    private Switch switchEffects;
    private Switch switchDebug;

    public SettingsFragment() {
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
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final ISoundController controller = CarcassonneApp.getSoundController();

        switchMusic = view.findViewById(R.id.switch_music);
        switchEffects = view.findViewById(R.id.switch_music);
        switchDebug = view.findViewById(R.id.switch_debugging);

        switchMusic.setChecked(controller.getBackground_music_state());

        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    controller.setBackground_music_state(true);
                    controller.startBackground_music();
                } else {
                    controller.stopBackground_music();
                    controller.setBackground_music_state(false);
                }
            }
        });

        switchDebug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CarcassonneApp.getGameController().debug(b);
            }
        });

        buttonMainMenu = view.findViewById(R.id.button_quit);
        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                INetworkController controller = CarcassonneApp.getNetworkController();

                CarcassonneMessage m = new CarcassonneMessage(CarcassonneMessage.PLAYER_EXIT_GAME);
                m.other = controller.getPlayerInfo(controller.getDevicePlayerNumber()).deviceName;
                controller.sendMessage(m);

                if (controller.isHost()) {
                    Salut network = controller.getNetwork();
                    network.stopNetworkService(false);
                }
                Intent i = new Intent(getContext(), Group2Activity.class);
                startActivity(i);
            }
        });

        return view;
    }


}
