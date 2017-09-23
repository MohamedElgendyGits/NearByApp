package com.android.nearbyapp.nearby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nearbyapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elgendy.
 */

public class VenuesListAdapter extends RecyclerView.Adapter<VenuesListAdapter.DataObjectHolder> {

    private ArrayList<VenueViewModel> mDataSet;
    private Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_place)
        ImageView placeImageView;

        @BindView(R.id.textView_place_name)
        TextView nameTextView;

        @BindView(R.id.textView_place_address)
        TextView addressTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public VenuesListAdapter(ArrayList<VenueViewModel> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_venues, parent, false);
        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        VenueViewModel venueViewModel = mDataSet.get(position);

        Picasso.with(context)
                .load(venueViewModel.getPlaceImageUrl())
                .placeholder(R.drawable.ic_no_data_96dp)
                .fit()
                .centerInside()
                .into(holder.placeImageView);

        holder.nameTextView.setText(venueViewModel.getName());
        holder.addressTextView.setText(venueViewModel.getAddress());
    }

    public void addItem(VenueViewModel dataObj) {
        mDataSet.add(dataObj);
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
