package com.capstone.android.instatour.src.search_detail.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.detail_posting.DetailPostingPostingActivity;

import java.util.ArrayList;
import static com.capstone.android.instatour.src.ApplicationClass.httpChange;


public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ItemViewHolder> {

    private ArrayList<String> listData = new ArrayList<>();
    private Activity activity;

    public PostingAdapter(Activity activity) {
        this.activity = activity;
    }
    // constructor

    public void addListData(ArrayList<String> tmp) {
        for(int i=0;i<tmp.size();i++){
            listData.add(tmp.get(i));
        }
    }

    public void setListData(ArrayList<String> listData) {
        this.listData = listData;
    }

    public ArrayList<String> getListData() {
        return listData;
    }

    public void clearData() {
        listData = new ArrayList<>();
//        this.listData.clear();
    }

    public void addData(String data) {
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
            heart = itemView.findViewById(R.id.item_posting_heart_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, DetailPostingPostingActivity.class);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i("PostingLonkClick", "YES");
                    return true;
                }
            });
        }

        void onBind(String data) {
                Log.i("PostingAdpater",httpChange(data));
                image.setBackgroundResource(R.drawable.black_head_img);

                Glide.with(activity)
                        .load(httpChange(data))
                        .fitCenter()
                        .into(image);
        }
        // set views
    }
}