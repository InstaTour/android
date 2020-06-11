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
import com.capstone.android.instatour.src.search_detail.interfaces.CallbackInterface;
import com.capstone.android.instatour.src.search_detail.models.PostResponse;
import com.capstone.android.instatour.src.search_detail.models.SearchDetailResponse;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;


public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ItemViewHolder> {

    private ArrayList<PostResponse> listData = new ArrayList<>();
    private Activity activity;
    private  CallbackInterface callbackInterface;

    public PostingAdapter(Activity activity, CallbackInterface callbackInterface) {
        this.activity = activity;
        this.callbackInterface = callbackInterface;
    }
    // constructor

    public void addListData(ArrayList<PostResponse> tmp) {
        for(int i=0;i<tmp.size();i++){
            listData.add(tmp.get(i));
        }
    }

    public void setListData(ArrayList<PostResponse> listData) {
        this.listData = listData;
    }

    public ArrayList<PostResponse> getListData() {
        return listData;
    }

    public void clearData() {
        listData = new ArrayList<>();
//        this.listData.clear();
    }

    public void addData(PostResponse data) {
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
                    callbackInterface.click(listData.get(getAdapterPosition()).getId());
//                    Intent intent = new Intent(activity, DetailPostingPostingActivity.class);
//                    intent.putExtra("id", listData.get(getAdapterPosition()).getId());
//                    intent.putExtra("location", )
//                    activity.startActivity(intent);
//                    activity.overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
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

        void onBind(PostResponse data) {
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

                if(data.isHearted()) {
                    heart.setBackgroundResource(R.drawable.full_heart_img);
                }
                else {
                    heart.setBackgroundResource(0);
                }
        }
        // set views
    }
}