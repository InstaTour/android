package com.capstone.android.instatour.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.datas.RecentData;

import java.util.ArrayList;

import static com.capstone.android.instatour.InstaTourApp.httpChange;


public class RecentSearchAdpater extends RecyclerView.Adapter<RecentSearchAdpater.ItemViewHolder> {

    private ArrayList<RecentData> listData = new ArrayList<>();
    private Activity activity;

    public RecentSearchAdpater(Activity activity) {
        this.activity = activity;
    }
    // constructor

    public void addListData(ArrayList<RecentData> tmp) {
        for(int i=0;i<tmp.size();i++){
            listData.add(tmp.get(i));
        }
    }

    public void setListData(ArrayList<RecentData> listData) {
        this.listData = listData;
    }

    public void clearData() {
        listData = new ArrayList<>();
    }

    public void addData(RecentData data) {
        listData.add(data);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
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
        private TextView search, count;

        ItemViewHolder(View itemView) {
            super(itemView);
            search = itemView.findViewById(R.id.item_search_label_tv);
            count = itemView.findViewById(R.id.item_search_count_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("PostingClick", "YES");
                }
            });
        }

        void onBind(RecentData data) {
            search.setText(data.getLocation());
            count.setText(data.getCount());
        }
        // set views
    }
}