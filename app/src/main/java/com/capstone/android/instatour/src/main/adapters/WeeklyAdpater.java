package com.capstone.android.instatour.src.main.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.main.models.MainTopClickHashTagResponse;
import com.capstone.android.instatour.src.search_detail.SearchDetailActivity;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class WeeklyAdpater extends RecyclerView.Adapter<WeeklyAdpater.ItemViewHolder> {

    private Activity activity;
    private ArrayList<MainTopClickHashTagResponse> listData = new ArrayList<>();

    public WeeklyAdpater(Activity activity) {
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

    public void  addData(MainTopClickHashTagResponse data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);

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
        private LinearLayout layout, layout2;
        private TextView views, count, name, related1, related2, related3, related4;

        ItemViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.item_week_image_iv);
            layout = itemView.findViewById(R.id.item_week_parent_layout);
            layout2 = itemView.findViewById(R.id.item_week_parent2_layout);
            views = itemView.findViewById(R.id.item_week_view_count_tv);
            count = itemView.findViewById(R.id.item_week_count_tv);
            name = itemView.findViewById(R.id.item_week_location_tv);

            related1 = itemView.findViewById(R.id.item_week_related1_tv);
            related2 = itemView.findViewById(R.id.item_week_related2_tv);
            related3 = itemView.findViewById(R.id.item_week_related3_tv);
            related4 = itemView.findViewById(R.id.item_week_related4_tv);

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

            if(data.getRelatives() != null && data.getRelatives().get(0) != null) {
                related1.setText("# " + data.getRelatives().get(0));
            }
            if(data.getRelatives() != null && data.getRelatives().get(1) != null) {
                related2.setText("# " + data.getRelatives().get(1));
            }
            if(data.getRelatives() != null && data.getRelatives().get(2) != null) {
                related3.setText("# " + data.getRelatives().get(2));
            }
            if(data.getRelatives() != null && data.getRelatives().get(3) != null) {
                related4.setText("# " + data.getRelatives().get(3));
            }

            layout.setBackground( (GradientDrawable) activity.getDrawable(R.drawable.main_item_radius_background));
            layout.setClipToOutline(true);

            layout2.setBackground(activity.getDrawable(R.drawable.main_item_radius_shoadow_background));
            layout2.setClipToOutline(true);

            // 상위 parent에서 gradient로 radius해서 자르고 cast
            // 상위 상위에서는 그림자 효과 주기
            // layer drawable는 gradient drawable로 cast 불 가능

            Glide.with(activity)
                    .load(httpChange(data.getImg_url()))
                    .fitCenter()
                    .into(picture);
        }
        // set views
    }
}