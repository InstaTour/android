package com.capstone.android.instatour.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.capstone.android.instatour.R;
import java.util.ArrayList;

import static com.capstone.android.instatour.InstaTourApp.httpChange;

public class MainWeeklyAdpater extends RecyclerView.Adapter<MainWeeklyAdpater.ItemViewHolder> {

    private Activity activity;
    private ArrayList<String> listData = new ArrayList<>();

    public MainWeeklyAdpater(Activity activity) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);

        view.setMinimumHeight(parent.getMeasuredWidth());
        int height = (int) (parent.getMeasuredWidth() / 2.2);
        view.setMinimumHeight(height);
        return new MainWeeklyAdpater.ItemViewHolder(view);
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
        private ImageView picture;
        private LinearLayout layout, layout2;

        ItemViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.weekly_item_image_iv);
            layout = itemView.findViewById(R.id.item_week_parent_layout);
            layout2 = itemView.findViewById(R.id.item_week_parent2_layout);
        }

        void onBind(String data) {
            int position = getAdapterPosition();


            layout.setBackground( (GradientDrawable) activity.getDrawable(R.drawable.main_item_radius_background));
            layout.setClipToOutline(true);

            layout2.setBackground(activity.getDrawable(R.drawable.main_item_radius_shoadow_background));
            layout2.setClipToOutline(true);

            // 상위 parent에서 gradient로 radius해서 자르고 cast
            // 상위 상위에서는 그림자 효과 주기
            // layer drawable는 gradient drawable로 cast 불 가능

            Glide.with(activity)
                    .load(httpChange(data))
                    .fitCenter()
                    .into(picture);
        }
        // set views
    }
}