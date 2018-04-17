package distudios.at.carcassonne.gui.lobby;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.nio.charset.IllegalCharsetNameException;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.INetworkController;

public class GroupOverviewFragment extends Fragment implements SalutDeviceCallback {

    public FloatingActionButton fab;
    public Button buttonRemoveGroup;
    public SalutDeviceFragment fragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupOverviewFragment newInstance(String param1, String param2) {
        GroupOverviewFragment fragment = new GroupOverviewFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_group_overview, container, false);

        fab = view.findViewById(R.id.fab_createGroup);
        buttonRemoveGroup = view.findViewById(R.id.button_remove_group);

        INetworkController controller = CarcassonneApp.getNetworkController();
        if (controller.isConnected()) {
            fab.hide();
            buttonRemoveGroup.setVisibility(View.VISIBLE);
        } else {
            fab.show();
            buttonRemoveGroup.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();
            }
        });

        fragment = (SalutDeviceFragment) getChildFragmentManager().findFragmentById(R.id.fragment_deviceListInGroup);

        buttonRemoveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Salut network = CarcassonneApp.getNetworkController().getNetwork();
                network.stopNetworkService(false);
                fab.show();
                buttonRemoveGroup.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void createGroup() {
        INetworkController controller = CarcassonneApp.getNetworkController();
        Salut network = controller.getNetwork();

        network.discoverNetworkServices(new SalutCallback() {
            @Override
            public void call() {

            }
        }, false);
        if (network.isDiscovering) {
            network.stopServiceDiscovery(false);
        }
        network.stopNetworkService(false);
        network.startNetworkService(this);

        fab.hide();
        buttonRemoveGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void call(SalutDevice salutDevice) {
        Snackbar.make(getView(), "Registered" + salutDevice.readableName, Snackbar.LENGTH_LONG).show();
        fragment.adapter.addDevice(salutDevice);
    }
}
