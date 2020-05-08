package com.capstone.android.instatour.activities;

import android.app.Activity;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.InstaTourApp;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.adapters.PostingAdapter;
import com.capstone.android.instatour.adapters.RecentSearchAdpater;
import com.capstone.android.instatour.datas.RecentData;
import com.capstone.android.instatour.datas.TestData;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        activity = this;

        initAdpater();
        tmpInitRecentData();

        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Intent intent = new Intent(activity, SearchDetailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);

                    return true;
                }
                return false;
            }
        });
    }

    public void tmpInitRecentData() {
        for(int i=0;i<10;i++){
            RecentData tmpData = new RecentData();
            tmpData.setCount("게시물 2.4M");
            tmpData.setLocation("#파리");
            mRecentAdpater.addData(tmpData);
        }
    }

    public void initAdpater() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRVRecent.setLayoutManager(linearLayoutManager);
        mRecentAdpater = new RecentSearchAdpater(this);
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
}