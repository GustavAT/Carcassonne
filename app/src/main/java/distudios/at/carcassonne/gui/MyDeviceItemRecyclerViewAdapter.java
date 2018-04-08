package distudios.at.carcassonne.gui;

import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import distudios.at.carcassonne.R;
import distudios.at.carcassonne.networking.OnWifiP2pDeviceActionEventListener;

import java.util.List;

public class MyDeviceItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDeviceItemRecyclerViewAdapter.ViewHolder> {

    private final List<WifiP2pDevice> mValues;
    private final OnWifiP2pDeviceActionEventListener actionListener;

    public MyDeviceItemRecyclerViewAdapter(List<WifiP2pDevice> items, OnWifiP2pDeviceActionEventListener listener) {
        mValues = items;
        actionListener = listener;
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

        String statusText = "";
        String colorString = "orange";
        switch (holder.mItem.status) {
            case WifiP2pDevice.AVAILABLE:
                statusText = "AVAILABLE";
                colorString = "green";
                holder.buttonAction.setVisibility(View.VISIBLE);
                holder.buttonAction.setText("Connect");
                break;
            case WifiP2pDevice.CONNECTED:
                statusText = "CONNECTED";
                colorString = "blue";
                holder.buttonAction.setVisibility(View.VISIBLE);
                holder.buttonAction.setText("Disconnect");
                break;
            case WifiP2pDevice.FAILED:
                statusText = "FAILED";
                colorString = "red";
                holder.buttonAction.setVisibility(View.INVISIBLE);
                break;
            case WifiP2pDevice.INVITED:
                statusText = "INVITED";
                colorString = "yellow";
                holder.buttonAction.setVisibility(View.INVISIBLE);
                break;
            case WifiP2pDevice.UNAVAILABLE:
                statusText = "UNAVAILABLE";
                colorString = "gray";
                holder.buttonAction.setVisibility(View.INVISIBLE);
                break;
        }

        holder.viewStatus.setText(statusText);
        holder.viewStatus.setTextColor(Color.parseColor(colorString));

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

        public WifiP2pDevice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            viewStatus = view.findViewById(R.id.text_status);
            viewDeviceName = view.findViewById(R.id.text_device);
            viewHostAddress = view.findViewById(R.id.text_host_adress);
            buttonAction = view.findViewById(R.id.button_action);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + viewDeviceName.getText() + "'";
        }
    }
}
