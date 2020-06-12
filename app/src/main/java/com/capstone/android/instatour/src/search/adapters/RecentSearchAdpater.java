package com.capstone.android.instatour.src.search.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.search.interfaces.RecentDeleteInterface;
import com.capstone.android.instatour.src.search.models.RecentData;
import com.capstone.android.instatour.src.search_detail.SearchDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class RecentSearchAdpater extends RecyclerView.Adapter<RecentSearchAdpater.ItemViewHolder> {

    private ArrayList<RecentData> listData = new ArrayList<>();
    private Activity activity;
    private RecentDeleteInterface deleteInterface;

    public RecentSearchAdpater(Activity activity, RecentDeleteInterface deleteInterface) {
        this.activity = activity;
        this.deleteInterface = deleteInterface;
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

    public void listToArrayList(List<RecentData> list) {
        this.listData.clear();
        this.listData.addAll(list);
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
        private TextView search, count, delete;

        ItemViewHolder(View itemView) {
            super(itemView);
            search = itemView.findViewById(R.id.item_search_label_tv);
            count = itemView.findViewById(R.id.item_search_count_tv);
            delete = itemView.findViewById(R.id.item_search_delete_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SearchDetailActivity.class);
                    intent.putExtra("location", listData.get(getAdapterPosition()).getLocation());
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteInterface.delete(listData.get(getAdapterPosition()));
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