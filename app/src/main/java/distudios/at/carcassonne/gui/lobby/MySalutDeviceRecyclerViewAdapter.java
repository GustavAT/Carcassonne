package distudios.at.carcassonne.gui.lobby;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.INetworkController;

import java.util.List;

public class MySalutDeviceRecyclerViewAdapter extends RecyclerView.Adapter<MySalutDeviceRecyclerViewAdapter.ViewHolder> {

    private final List<SalutDevice> mValues;
    private Salut network;

    public MySalutDeviceRecyclerViewAdapter(List<SalutDevice> items) {
        mValues = items;
        network = CarcassonneApp.getNetworkController().getNetwork();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_salutdevice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mInstanceName.setText(mValues.get(position).instanceName);
        holder.mDeviceName.setText(mValues.get(position).deviceName);
        holder.mMacAddress.setText(mValues.get(position).isRegistered + "");

        holder.mButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                network.registerWithHost(holder.mItem, new SalutCallback() {
                    @Override
                    public void call() {
                        Log.d("CONNECT", "success");
                        holder.mButtonAction.setEnabled(false);
                    }
                }, new SalutCallback() {
                    @Override
                    public void call() {
                        Log.d("CONNECT", "failed");
                    }
                });
            }
        });

        Log.d("LIST", "ADDED " + holder.mItem.instanceName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setDevices(List<SalutDevice> devices) {
        mValues.clear();
        mValues.addAll(devices);
        notifyDataSetChanged();
    }

    public void addDevice(SalutDevice device) {
        mValues.add(device);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mInstanceName;
        public final TextView mDeviceName;
        public final TextView mMacAddress;
        public final Button mButtonAction;
        public SalutDevice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mInstanceName = view.findViewById(R.id.textView_instanceName);
            mDeviceName = view.findViewById(R.id.textView_deviceName);
            mMacAddress = view.findViewById(R.id.textView_macAddress);
            mButtonAction = view.findViewById(R.id.button_service_action);
        }
    }
}
