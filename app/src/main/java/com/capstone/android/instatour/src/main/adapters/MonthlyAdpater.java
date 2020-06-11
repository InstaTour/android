package com.capstone.android.instatour.src.main.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.main.models.MainTopClickHashTagResponse;
import com.capstone.android.instatour.src.search_detail.SearchDetailActivity;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class MonthlyAdpater extends RecyclerView.Adapter<MonthlyAdpater.ItemViewHolder> {

    private Activity activity;
    private ArrayList<MainTopClickHashTagResponse> listData = new ArrayList<>();

    public MonthlyAdpater(Activity activity) {
        this.activity = activity;
    }
    // constructor


    public void setListData(ArrayList<MainTopClickHashTagResponse> listData) {
        this.listData = listData;
    }

    public ArrayList<MainTopClickHashTagResponse> getListData() {
        return listData;
    }

    public void clearData() {
        this.listData.clear();
    }

    public void addData(MainTopClickHashTagResponse data) {
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
        private TextView count, views, name;

        ItemViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.month_item_picture_iv);
            layout = itemView.findViewById(R.id.item_monthly_framelayout);
            name = itemView.findViewById(R.id.item_monthly_name_tv);
            views = itemView.findViewById(R.id.item_monthly_view_count_tv);
            count = itemView.findViewById(R.id.item_monthly_num_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, SearchDetailActivity.class);
                    intent.putExtra("location", listData.get(getAdapterPosition()).getId());
                    activity.startActivity(intent);
                }
            });
        }

        void onBind(MainTopClickHashTagResponse data) {
            int position = getAdapterPosition();

            name.setText(data.getId());
            views.setText(String.valueOf(data.getViews()));
            count.setText("게시물 "+ String.valueOf(data.getApx_num()));

            layout.setBackground(activity.getDrawable(R.drawable.main_item_radius_shoadow_background));
            layout.setClipToOutline(true);

            Glide.with(activity)
                    .load(httpChange(data.getImg_url()))
                    .fitCenter()
                    .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(16)))
                    .into(picture);

//            picture.setBackgroundResource(R.drawable.head_img);
        }
        // set views
    }
}