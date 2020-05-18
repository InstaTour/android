package com.capstone.android.instatour.src.detail_posting.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;

import java.util.ArrayList;
import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class DetailPostingImageAdapter extends RecyclerView.Adapter<DetailPostingImageAdapter.ItemViewHolder> {

    private Activity activity;
    private ArrayList<String> listData = new ArrayList<>();

    public DetailPostingImageAdapter(Activity activity) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_posting_image, parent, false);

        view.setMinimumWidth(parent.getMeasuredWidth());
        view.setMinimumHeight(parent.getMeasuredWidth());
        return new DetailPostingImageAdapter.ItemViewHolder(view);
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
        private TextView num;

        ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_detail_posting_iv);
            num = itemView.findViewById(R.id.item_detail_posting_num_tv);
        }

        void onBind(String data) {
            int position = getAdapterPosition();

            image.setBackgroundResource(R.drawable.black_head_img);

            Glide.with(activity)
                    .load(httpChange(data))
                    .centerCrop()
                    .into(image);

            num.setText(String.valueOf(position + 1) + " / " + String.valueOf(listData.size()));
        }
        // set views
    }
}