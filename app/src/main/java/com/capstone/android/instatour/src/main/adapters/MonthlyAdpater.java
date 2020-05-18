package com.capstone.android.instatour.src.main.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

public class MonthlyAdpater extends RecyclerView.Adapter<MonthlyAdpater.ItemViewHolder> {

    private Activity activity;
    private ArrayList<String> listData = new ArrayList<>();

    public MonthlyAdpater(Activity activity) {
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

    public void addData(String data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly, parent, false);

        view.setMinimumHeight(parent.getMeasuredWidth());
        int height = (int) (parent.getMeasuredWidth() / 2.2);
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
        private ImageView picture;
        private FrameLayout layout;

        ItemViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.month_item_picture_iv);
            layout = itemView.findViewById(R.id.item_monthly_framelayout);

        }

        void onBind(String data) {
            int position = getAdapterPosition();

            layout.setBackground(activity.getDrawable(R.drawable.main_item_radius_shoadow_background));
            layout.setClipToOutline(true);


            Glide.with(activity)
                    .load(httpChange(data))
                    .fitCenter()
                    .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(16)))
                    .into(picture);

//            picture.setBackgroundResource(R.drawable.head_img);
        }
        // set views
    }
}