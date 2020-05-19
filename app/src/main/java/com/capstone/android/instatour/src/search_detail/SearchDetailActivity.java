package com.capstone.android.instatour.src.search_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.edit_posting.EditPostingActivity;
import com.capstone.android.instatour.src.search_detail.adapters.PostingAdapter;
import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailActivityView;
import com.capstone.android.instatour.utils.SpaceItemDecoration;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class SearchDetailActivity extends BaseActivity implements SearchDetailActivityView {

    private TextView mTvLocation, mTvAboutCount, mTvDetailCount, mTvRelated;
    private ImageView mIvNickname, mIvFirst, mIvSecond;
    private RecyclerView mRVPosting;
    private PostingAdapter mPostingAdpater;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        initViews();
        init();
        initAdpater();
        tryGetTest();
    }

    public void init() {
        activity = this;
        mTvRelated.setText("#test1 #test2 #test3 #test4 #test5 #test6 #test7 #test1 #test2 #test3 #test4 #test5 #test6 #test7");
        mTvRelated.setMovementMethod((new ScrollingMovementMethod()));
    }

    @Override
    public void initViews() {
        mTvLocation = findViewById(R.id.search_detail_location_tv);
        mTvAboutCount = findViewById(R.id.search_detail_count_tv);
        mTvDetailCount = findViewById(R.id.search_detail_detail_count_tv);
        mTvRelated = findViewById(R.id.search_detail_related_tv);
        mIvNickname = findViewById(R.id.search_detail_picture_iv);
        mRVPosting = findViewById(R.id.search_detail_grid_rv);
        mIvFirst = findViewById(R.id.search_detail_first_iv);
        mIvSecond = findViewById(R.id.search_detail_second_iv);
    }

    public void initAdpater() {
        mRVPosting.setLayoutManager(new GridLayoutManager(this, 3));
        mPostingAdpater = new PostingAdapter(this);
        mRVPosting.setAdapter(mPostingAdpater);
    }

    private void tryGetTest() {
        showProgressDialog();

        final SearchDetailService mainService = new SearchDetailService(this);
        mainService.getTest();
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
    public void validateSuccess(ArrayList<String> list) {
        hideProgressDialog();

        mPostingAdpater.setListData(list);
        mPostingAdpater.notifyDataSetChanged();

        drawerCircleImage(httpChange(list.get(0).toString()));
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
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
}
