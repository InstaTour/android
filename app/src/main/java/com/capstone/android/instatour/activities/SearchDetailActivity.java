package com.capstone.android.instatour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.InstaTourApp;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.adapters.PostingAdapter;
import com.capstone.android.instatour.datas.TestData;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.capstone.android.instatour.InstaTourApp.catchAllThrowable;
import static com.capstone.android.instatour.InstaTourApp.httpChange;


public class SearchDetailActivity extends SuperActivity implements View.OnClickListener {

    private TextView mTvLocation, mTvAboutCount, mTvDetailCount, mTvRelated;
    private ImageView mIvPicture, mIvFirst, mIvSecond;
    private RecyclerView mRVPosting;
    private PostingAdapter mPostingAdpater;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        initViews();
        activity = this;
        mTvRelated.setText("#test1 #test2 #test3 #test4 #test5 #test6 #test7 #test1 #test2 #test3 #test4 #test5 #test6 #test7");

        getPosting();
        mTvRelated.setMovementMethod((new ScrollingMovementMethod()));
    }

    public void initAdpater() {
        mRVPosting.setLayoutManager(new GridLayoutManager(this, 3));
        mPostingAdpater = new PostingAdapter(this);
        mRVPosting.setAdapter(mPostingAdpater);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_detail_back_iv:
                finish();
                break;
            case R.id.search_detail_section_layout:
            case R.id.search_detail_section_text_tv:
                // start dialog
                break;
            case R.id.search_detail_plus_iv:
                Intent intent = new Intent(activity, EditPostingActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
        }
    }

    @Override
    void initViews() {
        mTvLocation = findViewById(R.id.search_detail_location_tv);
        mTvAboutCount = findViewById(R.id.search_detail_count_tv);
        mTvDetailCount = findViewById(R.id.search_detail_detail_count_tv);
        mTvRelated = findViewById(R.id.search_detail_related_tv);
        mIvPicture = findViewById(R.id.search_detail_picture_iv);
        mRVPosting = findViewById(R.id.search_detail_grid_rv);
        mIvFirst = findViewById(R.id.search_detail_first_iv);
        mIvSecond = findViewById(R.id.search_detail_second_iv);
    }

    public void getPosting() {
        InstaTourApp.getRetrofitMethod(getApplicationContext()).getTestPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        showProgressDialog();
                    }

                    @Override
                    public void onNext(final TestData res) {
                        Log.i("YES", "SUCCESS");

                        Glide.with(activity)
                                .load(R.drawable.insta_background)
                                .fitCenter()
                                .circleCrop()
                                .into(mIvFirst);

                        Glide.with(activity)
                                .load(R.drawable.white_background)
                                .fitCenter()
                                .circleCrop()
                                .into(mIvSecond);


                        Glide.with(activity)
                                .load(httpChange(res.getData().getData().get(0)))
                                .fitCenter()
                                .circleCrop()
                                .into(mIvPicture);

                        initAdpater();
                        mPostingAdpater.setListData(res.getData().getData());
                        mPostingAdpater.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("YES", "SUCCESS1");
                        Toast.makeText(getApplicationContext(), catchAllThrowable(getApplicationContext(), e), Toast.LENGTH_SHORT).show();

                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("YES", "SUCCESS2");
                        dismissProgressDialog();
                    }
                });
    }

}