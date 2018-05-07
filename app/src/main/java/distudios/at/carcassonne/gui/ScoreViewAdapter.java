package distudios.at.carcassonne.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import distudios.at.carcassonne.R;

public class ScoreViewAdapter extends ArrayAdapter<String> {


    private Context mContext;
    public ScoreViewAdapter(@NonNull Context context, String [] players) {
        super(context,R.layout.adapter_score_view ,players);
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        String points = "0";

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(R.layout.adapter_score_view, parent,false);

        String player = getItem(position);
        TextView playerName= convertView.findViewById(R.id.player_name);
        TextView playerPoints= convertView.findViewById(R.id.player_points);

        playerName.setText(player);
        playerPoints.setText(points);

        return convertView;

    }
}
