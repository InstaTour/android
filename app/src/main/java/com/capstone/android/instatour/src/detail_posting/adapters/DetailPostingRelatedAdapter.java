package com.capstone.android.instatour.src.detail_posting.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.capstone.android.instatour.R;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class DetailPostingRelatedAdapter extends RecyclerView.Adapter<DetailPostingRelatedAdapter.ItemViewHolder> {

    private Activity activity;
    private ArrayList<String> listData = new ArrayList<>();

    public DetailPostingRelatedAdapter(Activity activity) {
        this.activity = activity;
    }
    // constructor


    public void setListData(ArrayList<String> listData) {
        this.listData = listData;
    }

    public ArrayList<String> getListData() {
        return listData;
    }

    public void clearData() {
        this.listData.clear();
    }

    public void  addData(String data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_related_posting, parent, false);

//        view.setMinimumHeight((int)(parent.getMeasuredWidth()* 1.4));
//        view.setMinimumHeight(parent.getMeasuredWidth());
        return new DetailPostingRelatedAdapter.ItemViewHolder(view);
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
            image = itemView.findViewById(R.id.item_detail_related_posting_iv);
        }

        void onBind(String data) {
            int position = getAdapterPosition();

            Log.i("YESSDF", data);

            Glide.with(activity)
                    .load(httpChange(data))
                    .fitCenter()
                    .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(16)))
                    .into(image);
        }
        // set views
    }
}