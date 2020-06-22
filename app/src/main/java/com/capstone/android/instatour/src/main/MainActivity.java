package com.capstone.android.instatour.src.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.ApplicationClass;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.CheckPost.CheckPostlActivity;
import com.capstone.android.instatour.src.login.LoginActivity;
import com.capstone.android.instatour.src.main.adapters.MonthlyAdpater;
import com.capstone.android.instatour.src.main.adapters.ReviewerAdapter;
import com.capstone.android.instatour.src.main.adapters.WeeklyAdpater;
import com.capstone.android.instatour.src.main.interfaces.MainActivityView;
import com.capstone.android.instatour.src.main.models.MainTopClickResponse;
import com.capstone.android.instatour.src.main.models.MainTopReviewerResponse;
import com.capstone.android.instatour.src.main.models.MainUserResponse;
import com.capstone.android.instatour.src.search.SearchActivity;
import com.capstone.android.instatour.utils.SpaceItemDecoration;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class MainActivity extends BaseActivity implements MainActivityView {
    private WeeklyAdpater weeklyAdpater;
    private ViewPager2 mWeeklyVP2;
    private RecyclerView mMonthlyRV, mReviewerRV;
    private MonthlyAdpater monthlyAdpater;
    private ReviewerAdapter mReviewerAdapter;
    private Activity activity;
    private ImageView mIvFirst, mIvSecond, mIvNickname;
    private Intent intent;

    private TextView mTvDrawerName, mTvMainName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        init();
        initAdpater();
        initHamburgerBar();

        getUser();
        getTopReviwer();
        getTopMonthClick();
        getTopWeekClick();
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
        mTvMainName = findViewById(R.id.main_view_nickname_tv);
        mTvDrawerName = findViewById(R.id.main_drawer_nickname_tv);
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

    private void getUser() {
        showProgressDialog();

        final MainService mainService = new MainService(this);
        mainService.getUser();
    }

    private void getTopReviwer() {
        showProgressDialog();

        final MainService mainService = new MainService(this);
        mainService.getTopReviwer();
    }

    private void getTopMonthClick() {
        showProgressDialog();

        final MainService mainService = new MainService(this);
        mainService.getTopMonth();
    }

    private void getTopWeekClick() {
        showProgressDialog();

        final MainService mainService = new MainService(this);
        mainService.getTopWeek();
    }

    @Override
    public void validateUserSuccess(MainUserResponse user) {
        hideProgressDialog();

        if (user.getCode() != 200) {
            Toast.makeText(this, user.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (user.getData() != null) {
            if (user.getData().getUser() != null) {
                mTvMainName.setText(user.getData().getUser().getNickname());
                mTvDrawerName.setText(user.getData().getUser().getNickname());
            }
        }

        if(user.getData() != null && user.getData().getUser()!=null && user.getData().getUser().getProfile() != null) {
            String url = user.getData().getUser().getProfile();

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

            if (url.equals("null")) {
                Glide.with(activity)
                        .load(R.drawable.instatour_logo_img)
                        .fitCenter()
                        .circleCrop()
                        .into(mIvNickname);
            } else {
                Glide.with(activity)
                        .load(httpChange(url))
                        .fitCenter()
                        .circleCrop()
                        .into(mIvNickname);
            }
        }
    }

    @Override
    public void validateUserFailure(String message) {
        hideProgressDialog();
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateTopReviwerSuccess(MainTopReviewerResponse list) {
        hideProgressDialog();

        if (list.getData() != null) {
            for (int i = 0; i < list.getData().getUser().size(); i++) {
                mReviewerAdapter.addData(list.getData().getUser().get(i).getProfile());
            }
        }
        mReviewerAdapter.notifyDataSetChanged();
    }

    @Override
    public void validateTopReviwerFailure(String message) {
        hideProgressDialog();

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateTopClickMonthSuccess(MainTopClickResponse list) {
        hideProgressDialog();

        if (list.getData() != null) {
            monthlyAdpater.setListData(list.getData().getHashtags());
            monthlyAdpater.notifyDataSetChanged();
        }
    }

    @Override
    public void validateTopClickMonthFailure(String message) {
        hideProgressDialog();

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateTopClickWeekSuccess(MainTopClickResponse list) {
        hideProgressDialog();

        if (list.getData() != null) {
            weeklyAdpater.setListData(list.getData().getHashtags());
            weeklyAdpater.notifyDataSetChanged();
        }
    }

    @Override
    public void validateTopClickWeekFailure(String message) {
        hideProgressDialog();

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_view_search_layout:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
            case R.id.main_drawer_my_logout_tv:
                AWSMobileClient.getInstance().signOut();

                SharedPreferences mSharedPreferences = getSharedPreferences(ApplicationClass.TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.remove(X_ACCESS_TOKEN);

                editor.commit();

                intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                finish();
                break;
            case R.id.main_drawer_my_information_tv:
                intent = new Intent(activity, CheckPostlActivity.class);
                intent.putExtra("id", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
            case R.id.main_drawer_steam_tv:
                intent = new Intent(activity, CheckPostlActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
        }
    }
}
