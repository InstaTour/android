package com.capstone.android.instatour.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.InstaTourApp;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.adapters.MainMonthlyAdpater;
import com.capstone.android.instatour.adapters.MainReviewerAdapter;
import com.capstone.android.instatour.adapters.MainWeeklyAdpater;
import com.capstone.android.instatour.datas.TestData;
import com.capstone.android.instatour.utils.SpaceItemDecoration;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

import static com.capstone.android.instatour.InstaTourApp.catchAllThrowable;
import static com.capstone.android.instatour.InstaTourApp.httpChange;


public class MainActivity extends SuperActivity implements View.OnClickListener {

    private MainWeeklyAdpater weeklyAdpater;
    private ViewPager2 mWeeklyVP2;
    private RecyclerView mMonthlyRV, mReviewerRV;
    private MainMonthlyAdpater monthlyAdpater;
    private MainReviewerAdapter mReviewerAdapter;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayout();

        initViews();
        init();

        getTmpWeeklyHttp();

    }

    public void init() {
        activity = this;
        weeklyAdpater = new MainWeeklyAdpater(this);
        mWeeklyVP2.setAdapter(weeklyAdpater);
        mWeeklyVP2.setOrientation(mWeeklyVP2.ORIENTATION_HORIZONTAL);
        mWeeklyVP2.setPageTransformer(new MarginPageTransformer(50));

        LinearLayoutManager monthlyLinearManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        monthlyAdpater = new MainMonthlyAdpater(this);
        mMonthlyRV.setAdapter(monthlyAdpater);
        mMonthlyRV.setLayoutManager(monthlyLinearManager);
        SpaceItemDecoration dividerItemDecoration = new SpaceItemDecoration(50);
        mMonthlyRV.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager reviewerManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mReviewerAdapter = new MainReviewerAdapter(this);
        mReviewerRV.setAdapter(mReviewerAdapter);
        mReviewerRV.setLayoutManager(reviewerManager);
        mReviewerRV.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_view_search_layout:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
        }
    }

    @Override
    void initViews() {
        mWeeklyVP2 = findViewById(R.id.main_view_weekly_viewpager2);
        mMonthlyRV = findViewById(R.id.main_view_month_rv);
        mReviewerRV = findViewById(R.id.main_view_reviwer_rv);
    }
    public void initializeLayout()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0); // 좌,우 여백
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed
        );
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    // set toolbar & main_view

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            AppFinishDialog dialog = new AppFinishDialog(activity, finishInterface);
        }
    }

    public void getTmpWeeklyHttp() {
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
                        Log.i("YES","SUCCESS");


                        weeklyAdpater.setListData(res.getData().getData());
                        weeklyAdpater.notifyDataSetChanged();

                        monthlyAdpater.setListData(res.getData().getData());
                        monthlyAdpater.notifyDataSetChanged();

                        mReviewerAdapter.setListData(res.getData().getData());
                        mReviewerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("YES","SUCCESS1");
                        Toast.makeText(getApplicationContext(), catchAllThrowable(getApplicationContext(), e), Toast.LENGTH_SHORT).show();

                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("YES","SUCCESS2");
                        dismissProgressDialog();
                    }
                });
    }
}