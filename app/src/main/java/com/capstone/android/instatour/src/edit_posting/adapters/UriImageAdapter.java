package com.capstone.android.instatour.src.edit_posting.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;

import java.util.ArrayList;

public class UriImageAdapter extends RecyclerView.Adapter<UriImageAdapter.ItemViewHolder> {

    private Activity activity;
    private ArrayList<Uri> listData = new ArrayList<>();

    public UriImageAdapter(Activity activity) {
        this.activity = activity;
    }
    // constructor


    public void setListData(ArrayList<Uri> listData) {
        this.listData = listData;
    }

    public ArrayList<Uri> getListData() {
        return listData;
    }

    public void clearData() {
        this.listData.clear();
    }

    public void addData(Uri data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_posting_image, parent, false);

        view.setMinimumWidth(parent.getMeasuredWidth());
        view.setMinimumHeight(parent.getMeasuredWidth());
        return new UriImageAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_detail_posting_iv);
        }

        void onBind(Uri data) {
            Glide.with(activity)
                    .load(data)
                    .into(image);
        }
        // set views
    }
}