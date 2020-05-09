package com.capstone.android.instatour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.AppDatabase;
import com.capstone.android.instatour.InstaTourApp;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.adapters.PostingAdapter;
import com.capstone.android.instatour.adapters.RecentSearchAdpater;
import com.capstone.android.instatour.datas.RecentData;
import com.capstone.android.instatour.datas.TestData;
import com.capstone.android.instatour.interfaces.RecentDataDao;
import com.capstone.android.instatour.interfaces.RecentDeleteInterface;
import com.capstone.android.instatour.utils.Utils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.capstone.android.instatour.InstaTourApp.catchAllThrowable;
import static com.capstone.android.instatour.InstaTourApp.httpChange;


public class SearchActivity extends SuperActivity implements View.OnClickListener {

    private Activity activity;
    private EditText mEtSearch;
    private RecyclerView mRVRecent;
    private RecentSearchAdpater mRecentAdpater;
    private AppDatabase db;
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
        activity = this;

        initAdpater();

        mEtSearch.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                insert();

                Intent intent = new Intent(activity, SearchDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);

                return true;
            }
            return false;
        });

        // ui 갱신
        AppDatabase.getInstance(this).recentDataDao().getAll().observe(this, recentData ->  {
            mRecentAdpater.listToArrayList(recentData);
            mRecentAdpater.notifyDataSetChanged();
        });


        // 버튼 클릭 시 db insert
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
            case R.id.search_search_back_tv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
                break;
            case R.id.search_detail_section_layout:
            case R.id.search_detail_section_text_tv:
                // start dialog
                break;
            case R.id.search_search_iv:
                insert();
                Intent intent = new Intent(this, SearchDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
        }
    }

    @Override
    void initViews() {
        mRVRecent = findViewById(R.id.search_recent_search_rv);
        mEtSearch = findViewById(R.id.search_search_et);
    }

    public void insert() {
        new InsertAyncTask(AppDatabase.getInstance(this).recentDataDao(), true).execute(new RecentData(mEtSearch.getText().toString(), Utils.getNowTime()));
    }

    private static class InsertAyncTask extends AsyncTask <RecentData, Void, Void> {
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