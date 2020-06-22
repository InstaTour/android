package com.capstone.android.instatour.src.search_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.capstone.android.instatour.src.detail_posting.DetailPostingPostingActivity;
import com.capstone.android.instatour.src.edit_posting.EditPostingActivity;
import com.capstone.android.instatour.src.search_detail.adapters.PostingAdapter;
import com.capstone.android.instatour.src.search_detail.dialogs.SectionDialog;
import com.capstone.android.instatour.src.search_detail.interfaces.CallbackInterface;
import com.capstone.android.instatour.src.search_detail.interfaces.DialogSectionInterface;
import com.capstone.android.instatour.src.search_detail.interfaces.SearchDetailActivityView;
import com.capstone.android.instatour.src.search_detail.models.SearchDetailResponse;
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
    private String location, section;

    public int mPage = 0;
    public boolean mNoMoreItem = false;
    public boolean mLoadLock = false;
    private boolean mIsFirst = true;

    private CallbackInterface callbackInterface = new CallbackInterface() {
        @Override
        public void click(String id) {
            Intent intent = new Intent(activity, DetailPostingPostingActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("location", location);
                    intent.putExtra("section", section);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
        }

        @Override
        public void heart(String id, boolean select) {
            Log.i("SDVSDV", String.valueOf(select));
           heartCommunication(id, select);
        }
    };

    private DialogSectionInterface dialogSectionInterface = new DialogSectionInterface() {
        @Override
        public void click(String name) {
            section = name;

            mPage =0;
            mNoMoreItem = false;
            mLoadLock = false;
            mIsFirst = true;

            getPost();
        }
    };

    public void heartCommunication(String id, boolean select) {
        final SearchDetailService detailService = new SearchDetailService(this);
        if(select) {
            detailService.deleteHeart(id);
        }
        else {
            detailService.postHeart(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        initViews();
        init();
        initAdpater();
        getPost();
    }

    public void init() {
        activity = this;
        location = getIntent().getStringExtra("location");
        section = "SEC_ALL";

        mTvLocation.setText(getIntent().getStringExtra("location"));

        mRVPosting.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if((mRVPosting.getLayoutManager())==null){
                    return;
                }
                int lastVisiblePosition = ((GridLayoutManager)mRVPosting.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                if(mRVPosting.getAdapter()==null){
                    return;
                }
                int itemTotalCount = mRVPosting.getAdapter().getItemCount();

                if(lastVisiblePosition > itemTotalCount * 0.7){
                    if (!mLoadLock) {
                        mLoadLock = true;
                        if (!mNoMoreItem) {
                            getPost();
                        }
                    }
                }
            }
        });
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
        mPostingAdpater = new PostingAdapter(this, callbackInterface);
        mRVPosting.setAdapter(mPostingAdpater);
    }

    private void getPost() {
        showProgressDialog();

        final SearchDetailService mainService = new SearchDetailService(this);
        mainService.getSearch(location, section, mPage, 30);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_detail_back_iv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
                break;
            case R.id.search_detail_plus_iv:
                Intent intent = new Intent(activity, EditPostingActivity.class);
                intent.putExtra("location", location);
                intent.putExtra("section", section);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;

            case R.id.search_detail_section_text_tv:
            case R.id.search_detail_section_layout:
                SectionDialog mDialog = new SectionDialog(activity, dialogSectionInterface, section);
                break;
        }
    }

    @Override
    public void validateSuccess(SearchDetailResponse response) {
        hideProgressDialog();


        if(mIsFirst) {
            String tmp = "";
            if(response.getData() == null) {
                return;
            }
            if(response.getData().getRelatives() != null) {
                for(int i=0;i<response.getData().getRelatives().size();i++) {
                    tmp= tmp + "#" + response.getData().getRelatives().get(i) + " ";
                }
                mTvRelated.setText(tmp);
                mTvRelated.setMovementMethod((new ScrollingMovementMethod()));
            }

            mTvDetailCount.setText(String.valueOf(response.getData().getNum()));
            mTvAboutCount.setText(String.valueOf(response.getData().getApx_num()));

            if(response.getData().getPosts() != null && response.getData().getPosts().size()!=0) {
                drawerCircleImage(response.getData().getPosts().get(0).getImgUrl().get(0));
            }

            mIsFirst = false;
        }

        if(response.getData().getPosts().size() ==0 || response.getData().getPosts().size() % 30 !=0) {
            mNoMoreItem =  true;
        }

        if(mPage ==0) {
            mPostingAdpater.setListData(response.getData().getPosts());
        }
        else {
            mPostingAdpater.addListData(response.getData().getPosts());
            mPostingAdpater.notifyItemRangeChanged(0, mPostingAdpater.getListData().size());
        }
        mPostingAdpater.notifyDataSetChanged();
        mPage += response.getData().getPosts().size();
        mLoadLock = false;
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        System.out.println(message);
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    @Override
    public void validateHeartSuccess(String message) {

    }

    @Override
    public void validateHeartFailure(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
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
