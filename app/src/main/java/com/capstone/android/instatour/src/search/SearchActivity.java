package com.capstone.android.instatour.src.search;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.AppDatabase;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.search.adapters.RecentSearchAdpater;
import com.capstone.android.instatour.src.search.dao.RecentDataDao;
import com.capstone.android.instatour.src.search.interfaces.RecentDeleteInterface;
import com.capstone.android.instatour.src.search.models.RecentData;
import com.capstone.android.instatour.src.search_detail.SearchDetailActivity;
import com.capstone.android.instatour.utils.SpaceItemDecoration;
import com.capstone.android.instatour.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class SearchActivity extends BaseActivity {

    private Activity activity;
    private EditText mEtSearch;
    private RecyclerView mRVRecent;
    private RecentSearchAdpater mRecentAdpater;
    private RecentDeleteInterface deleteInterface = new RecentDeleteInterface() {
        @Override
        public void delete(RecentData data) {
            new InsertAyncTask(AppDatabase.getInstance(activity).recentDataDao(), false).execute(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        initAdpater();
        init();
    }

    public void init() {
        activity = this;
        mEtSearch.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                insert();

                Intent intent = new Intent(this, SearchDetailActivity.class);
                intent.putExtra("location", mEtSearch.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);

                return true;
            }
            return false;
        });
        // key event

        AppDatabase.getInstance(this).recentDataDao().getAll().observe(this, recentData ->  {
            mRecentAdpater.listToArrayList(recentData);
            mRecentAdpater.notifyDataSetChanged();
        });
        // set database
    }

    public void initAdpater() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mRVRecent.setLayoutManager(linearLayoutManager);
        mRecentAdpater = new RecentSearchAdpater(this, deleteInterface);
        mRVRecent.setAdapter(mRecentAdpater);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back_tv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
                break;
            case R.id.search_search_iv:
                insert();
                Intent intent = new Intent(this, SearchDetailActivity.class);
                intent.putExtra("location", mEtSearch.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
            case R.id.search_search_erase_iv:
                mEtSearch.setText("");
                break;
        }
    }

    @Override
    public void initViews() {
        mRVRecent = findViewById(R.id.search_recent_search_rv);
        mEtSearch = findViewById(R.id.search_search_et);
    }

    public void insert() {
        new InsertAyncTask(AppDatabase.getInstance(this).recentDataDao(), true).execute(new RecentData(mEtSearch.getText().toString(), Utils.getNowTime()));
    }

    private static class InsertAyncTask extends AsyncTask<RecentData, Void, Void> {
        private RecentDataDao mRecentDataDao;
        private boolean select;

        public InsertAyncTask(RecentDataDao recentDataDao, boolean select) {
            this.mRecentDataDao = recentDataDao;
            this.select = select;
        }

        @Override
        protected Void doInBackground(RecentData... recentData) {
            if(select) {
                mRecentDataDao.insert(recentData[0]);
            }
            else {
                mRecentDataDao.delete(recentData[0]);
            }

            return  null;
        }
    }
}
