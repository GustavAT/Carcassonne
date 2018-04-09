package distudios.at.carcassonne.gui;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.OnWifiP2pDeviceActionEventListener;

import java.util.List;

public class MyDeviceItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDeviceItemRecyclerViewAdapter.ViewHolder> {

    private final List<WifiP2pDevice> mValues;
    private final OnWifiP2pDeviceActionEventListener actionListener;
    private final Context context;

    public MyDeviceItemRecyclerViewAdapter(List<WifiP2pDevice> items, OnWifiP2pDeviceActionEventListener listener, Context context) {
        mValues = items;
        actionListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_deviceitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.viewDeviceName.setText(holder.mItem.deviceName);
        holder.viewHostAddress.setText(holder.mItem.deviceAddress);

        holder.progressBar.setVisibility(View.INVISIBLE);

        int statusId;
        int colorId;
        switch (holder.mItem.status) {
            case WifiP2pDevice.AVAILABLE:
                statusId = R.string.status_available;
                colorId = R.color.colorAvailable;
                if (CarcassonneApp.getNetworkController().canConnect()) {
                    holder.buttonAction.setVisibility(View.VISIBLE);
                } else {
                    holder.buttonAction.setVisibility(View.INVISIBLE);
                }
                holder.buttonAction.setText(context.getString(R.string.text_connect));
                break;
            case WifiP2pDevice.CONNECTED:
                statusId = R.string.status_connected;
                colorId = R.color.colorConnected;
                holder.buttonAction.setVisibility(View.VISIBLE);
                holder.buttonAction.setText(context.getString(R.string.text_disconnect));
                break;
            case WifiP2pDevice.FAILED:
                statusId = R.string.status_failed;
                colorId = R.color.colorFailed;
                holder.buttonAction.setVisibility(View.INVISIBLE);
                break;
            case WifiP2pDevice.INVITED:
                statusId = R.string.status_invited;
                colorId = R.color.colorInvited;
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.buttonAction.setVisibility(View.VISIBLE);
                holder.buttonAction.setText(context.getString(R.string.text_cancel));
                break;
            default:
                statusId = R.string.status_unavailable;
                colorId = R.color.colorUnavailable;
                holder.buttonAction.setVisibility(View.INVISIBLE);
                break;
        }

        holder.viewStatus.setText(context.getString(statusId));
        holder.viewStatus.setTextColor(ResourcesCompat.getColor(context.getResources(), colorId, null));

        holder.buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null) {
                    actionListener.onAction(holder.mItem, holder);
                }
            }
        });
    }

    public void setDevices(List<WifiP2pDevice> devices) {
        this.mValues.clear();
        this.mValues.addAll(devices);
    }

    public void addDevice(WifiP2pDevice device) {
        boolean existing = false;
        for (WifiP2pDevice rec : mValues) {
            if (rec.deviceAddress.equals(device.deviceAddress)) {
                existing = true;
                break;
            }
        }

        if (!existing) {
            mValues.add(device);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView viewStatus;
        public final TextView viewDeviceName;
        public final TextView viewHostAddress;
        public final Button buttonAction;
        public final ProgressBar progressBar;

        public WifiP2pDevice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            viewStatus = view.findViewById(R.id.text_status);
            viewDeviceName = view.findViewById(R.id.text_device);
            viewHostAddress = view.findViewById(R.id.text_host_adress);
            buttonAction = view.findViewById(R.id.button_action);
            progressBar = view.findViewById(R.id.progress_connection);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + viewDeviceName.getText() + "'";
        }
    }
}
