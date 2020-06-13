package com.capstone.android.instatour.src.CheckPost.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.CheckPost.models.CheckPostDataResponse;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;


public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ItemViewHolder> {

    private ArrayList<CheckPostDataResponse> listData = new ArrayList<>();
    private Activity activity;

    public PostingAdapter(Activity activity) {
        this.activity = activity;
    }
    // constructor

    public void addListData(ArrayList<CheckPostDataResponse> tmp) {
        for(int i=0;i<tmp.size();i++){
            listData.add(tmp.get(i));
        }
    }

    public void setListData(ArrayList<CheckPostDataResponse> listData) {
        this.listData = listData;
    }

    public ArrayList<CheckPostDataResponse> getListData() {
        return listData;
    }

    public void clearData() {
        listData = new ArrayList<>();
//        this.listData.clear();
    }

    public void addData(CheckPostDataResponse data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posting, parent, false);
        int height = parent.getMeasuredWidth() / 3;
        Log.i("Height", String.valueOf(height));
        view.setMinimumWidth(height);
        view.setMinimumHeight(height);
        return new ItemViewHolder(view);
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
        private ImageView image, heart;

        ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_posting_iv);
        }

        void onBind(CheckPostDataResponse data) {
                image.setBackgroundResource(R.drawable.black_head_img);

                if(data.getImgUrl() != null) {
                    Glide.with(activity)
                            .load(httpChange(data.getImgUrl().get(0)))
                            .fitCenter()
                            .into(image);
                }
                else {
                    Glide.with(activity)
                            .load(R.drawable.travel_shape_img)
                            .fitCenter()
                            .into(image);
                }
        }
        // set views
    }
}