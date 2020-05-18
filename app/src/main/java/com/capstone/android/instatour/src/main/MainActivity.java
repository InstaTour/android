package com.capstone.android.instatour.src.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.main.adapters.MonthlyAdpater;
import com.capstone.android.instatour.src.main.adapters.ReviewerAdapter;
import com.capstone.android.instatour.src.main.adapters.WeeklyAdpater;
import com.capstone.android.instatour.src.main.interfaces.MainActivityView;
import com.capstone.android.instatour.src.search.SearchActivity;
import com.capstone.android.instatour.utils.SpaceItemDecoration;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class MainActivity extends BaseActivity implements MainActivityView {
    private WeeklyAdpater weeklyAdpater;
    private ViewPager2 mWeeklyVP2;
    private RecyclerView mMonthlyRV, mReviewerRV;
    private MonthlyAdpater monthlyAdpater;
    private ReviewerAdapter mReviewerAdapter;
    private Activity activity;
    private ImageView mIvFirst, mIvSecond, mIvNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        init();
        initAdpater();
        initHamburgerBar();

        tryGetTest();
    }

    public void init() {
        activity = this;
    }

    public void initAdpater() {
        weeklyAdpater = new WeeklyAdpater(this);
        mWeeklyVP2.setAdapter(weeklyAdpater);
        mWeeklyVP2.setOrientation(mWeeklyVP2.ORIENTATION_HORIZONTAL);
        mWeeklyVP2.setPageTransformer(new MarginPageTransformer(50));

        LinearLayoutManager monthlyLinearManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        monthlyAdpater = new MonthlyAdpater(this);
        mMonthlyRV.setAdapter(monthlyAdpater);
        mMonthlyRV.setLayoutManager(monthlyLinearManager);
        SpaceItemDecoration dividerItemDecoration = new SpaceItemDecoration(50);
        mMonthlyRV.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager reviewerManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mReviewerAdapter = new ReviewerAdapter(this);
        mReviewerRV.setAdapter(mReviewerAdapter);
        mReviewerRV.setLayoutManager(reviewerManager);
        mReviewerRV.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void initViews() {
        mWeeklyVP2 = findViewById(R.id.main_view_weekly_viewpager2);
        mMonthlyRV = findViewById(R.id.main_view_month_rv);
        mReviewerRV = findViewById(R.id.main_view_reviwer_rv);
        mIvFirst = findViewById(R.id.main_drawer_first_iv);
        mIvSecond = findViewById(R.id.main_drawer_second_iv);
        mIvNickname = findViewById(R.id.main_drawer_user_iv);
    }
    public void initHamburgerBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
    // set toolbar & view_main

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            AppFinishDialog dialog = new AppFinishDialog(activity, finishInterface);
            finish();
        }
    }

    private void tryGetTest() {
        showProgressDialog();

        final MainService mainService = new MainService(this);
        mainService.getTest();
    }

    @Override
    public void validateSuccess(ArrayList<String> list) {
        hideProgressDialog();

        weeklyAdpater.setListData(list);
        weeklyAdpater.notifyDataSetChanged();

        monthlyAdpater.setListData(list);
        monthlyAdpater.notifyDataSetChanged();

        mReviewerAdapter.setListData(list);
        mReviewerAdapter.notifyDataSetChanged();

        drawerCircleImage(httpChange(list.get(0).toString()));
    }

    public void drawerCircleImage(String data) {
        Glide.with(activity)
                .load(R.drawable.main_color_circle_img)
                .fitCenter()
                .circleCrop()
                .into(mIvFirst);

        Glide.with(activity)
                .load(R.drawable.white_background)
                .fitCenter()
                .circleCrop()
                .into(mIvSecond);

        Glide.with(activity)
                .load(httpChange(data))
                .fitCenter()
                .circleCrop()
                .into(mIvNickname);
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_view_search_layout:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
        }
    }
}
