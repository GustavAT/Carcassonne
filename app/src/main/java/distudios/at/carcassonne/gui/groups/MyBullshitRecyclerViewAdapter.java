package distudios.at.carcassonne.gui.groups;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import distudios.at.carcassonne.R;

import java.util.ArrayList;
import java.util.List;

public class MyBullshitRecyclerViewAdapter extends RecyclerView.Adapter<MyBullshitRecyclerViewAdapter.ViewHolder> {

    private List<String> mValues;

    public MyBullshitRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bullshit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position));
        holder.mContentView.setText(mValues.get(position));
    }

    public void fill() {
        mValues.add("sdjfksjldflskdfk");
        mValues.add("332423");
        mValues.add("346DFGHRD");
        mValues.add("nmg6745");
//        notifyDataSetChanged();
        notifyItemRangeChanged(0, 4);
    }

    public void clear() {
//        mValues.clear();
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
