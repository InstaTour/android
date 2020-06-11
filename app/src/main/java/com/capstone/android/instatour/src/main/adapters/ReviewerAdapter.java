package com.capstone.android.instatour.src.main.adapters;

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

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;


public class ReviewerAdapter extends RecyclerView.Adapter<ReviewerAdapter.ItemViewHolder> {

    private ArrayList<String> listData = new ArrayList<>();
    private Activity activity;

    public ReviewerAdapter(Activity activity) {
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

    public void clearData() {
        listData = new ArrayList<>();
    }

    public void addData(String data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviewr, parent, false);
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
        private ImageView image, bkg1, bkg2;

        ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_reviewr_iv);
            bkg1 = itemView.findViewById(R.id.item_review_first_iv);
            bkg2 = itemView.findViewById(R.id.item_review_second_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("PostingClick", "YES");
                }
            });
        }

        void onBind(String data) {
            Glide.with(activity)
                    .load(R.drawable.main_color_circle_img)
                    .fitCenter()
                    .circleCrop()
                    .into(bkg1);

            Glide.with(activity)
                    .load(R.drawable.white_background)
                    .fitCenter()
                    .circleCrop()
                    .into(bkg2);

            if(data.equals("null") || data.equals("")) {
                Glide.with(activity)
                        .load(R.drawable.instatour_logo_img)
                        .fitCenter()
                        .circleCrop()
                        .into(image);
            }
            else {
                Glide.with(activity)
                        .load(httpChange(data))
                        .fitCenter()
                        .circleCrop()
                        .into(image);
            }
        }
        // set views
    }
}