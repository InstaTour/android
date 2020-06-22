package com.capstone.android.instatour.src.detail_posting;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.detail_posting.adapters.DetailPostingImageAdapter;
import com.capstone.android.instatour.src.detail_posting.adapters.DetailPostingRelatedAdapter;
import com.capstone.android.instatour.src.detail_posting.dialogs.DetailPostingRatingDialog;
import com.capstone.android.instatour.src.detail_posting.interfaces.DetailPostingActivityView;
import com.capstone.android.instatour.src.detail_posting.interfaces.DialogRatingInterface;
import com.capstone.android.instatour.src.detail_posting.models.DetailPostResponse;
import com.capstone.android.instatour.utils.SpaceItemDecoration;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class DetailPostingPostingActivity extends BaseActivity implements DetailPostingActivityView {

    private Activity activity;
    private TextView mTvLocation, mTvSection, mTvReviewCount, mTvContent, mTvDate, mTvNickname, mTvPostingCount, mTvProgressBarCount;
    private ImageView mIvHeart, mIvStars, mIvNickname, mIvFirst, mIvSecond;
    private ProgressBar mPBManner;
    private ViewPager2 mVPImage;
    private RecyclerView mRVRelate;
    private DetailPostingImageAdapter mImageAdapter;
    private DetailPostingRelatedAdapter mRelatedAdpater;
    private String id = "";

    private DialogRatingInterface dialogRatingInterface = new DialogRatingInterface() {
        @Override
        public void rating(float value) {
           putStart(value);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_posting);

        initViews();
        init();
        initAdpater();

        getDetailPost();
    }

    public void initAdpater() {
        mImageAdapter = new DetailPostingImageAdapter(this);
        mVPImage.setAdapter(mImageAdapter);
        mVPImage.setOrientation(mVPImage.ORIENTATION_HORIZONTAL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRelatedAdpater = new DetailPostingRelatedAdapter(this);
        mRVRelate.setAdapter(mRelatedAdpater);
        mRVRelate.setLayoutManager(linearLayoutManager);
        SpaceItemDecoration dividerItemDecoration = new SpaceItemDecoration(50);
        mRVRelate.addItemDecoration(dividerItemDecoration);
    }

    public void init() {
        activity = this;
        id = getIntent().getStringExtra("id");

        Log.i("SDVSDV", id);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_posting_writer_layout:
                Uri uri = Uri.parse("https://www.instagram.com/p/"+id);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com")));
                }
                break;
            case R.id.detail_posting_star_iv:
                DetailPostingRatingDialog mDialog = new DetailPostingRatingDialog(activity, 0, dialogRatingInterface);
                break;

            case R.id.detail_posting_back_iv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
                break;
        }
    }

    @Override
    public void initViews() {
        mVPImage = findViewById(R.id.detail_posting_main_image_vp);
        mRVRelate = findViewById(R.id.detail_posting_related_rv);
        mIvNickname = findViewById(R.id.detail_posting_user_iv);
        mIvFirst = findViewById(R.id.detail_posting_first_iv);
        mIvSecond = findViewById(R.id.detail_posting_second_iv);
        mTvLocation = findViewById(R.id.detail_posting_location_tv);
        mTvSection = findViewById(R.id.detail_posting_section_tv);
        mTvReviewCount = findViewById(R.id.detail_posting_star_count_tv);
        mTvContent = findViewById(R.id.detail_posting_content_tv);
        mTvDate = findViewById(R.id.detail_posting_date_tv);
        mTvNickname = findViewById(R.id.detail_posting_nickname_tv);
        mTvPostingCount = findViewById(R.id.detail_posting_post_count_tv);
        mIvStars = findViewById(R.id.detail_posting_star_iv);
        mPBManner = findViewById(R.id.detail_posting_manner_pb);
        mTvProgressBarCount = findViewById(R.id.detail_posting_manner_tv);
    }

    private void getDetailPost() {
        showProgressDialog();

        final DetailPostingService mainService = new DetailPostingService(this);
        mainService.getDetailPost(id);
    }

    private void putStart(float value) {
        Log.i("SDVSDV", "DSVSDV");
        final DetailPostingService mainService = new DetailPostingService(this);
        mainService.putStars(id, value);
    }

    @Override
    public void validateDetailPostSuccess(DetailPostResponse response) {
        hideProgressDialog();

        mImageAdapter.setListData(response.getData().getPost().getImgList());
        mImageAdapter.notifyDataSetChanged();

        mRelatedAdpater.setListData(response.getData().getPost().getImgList());
        mRelatedAdpater.notifyDataSetChanged();

        mTvLocation.setText(getIntent().getStringExtra("location"));
        String section = getIntent().getStringExtra("section");
        if(section.equals("SEC_ALL")) {
            section = "전체";
        }
        else if(section.equals("SEC_SIGHTS")) {
            section = "관광지";
        }
        else if(section.equals("SEC_FOOD")) {
            section = "맛집";
        }
        Log.i("SDVSDV", section);

        mTvSection.setText(section);
        mTvDate.setText(response.getData().getPost().getDate().substring(0, 11));
        mTvContent.setText(response.getData().getPost().getContent());

        if((int) response.getData().getAvgRates() ==0) {
            mIvStars.setBackgroundResource(R.drawable.star_0_img);
        }
        else if((int) response.getData().getAvgRates() ==1) {
            mIvStars.setBackgroundResource(R.drawable.star_1_img);
        }
        else if((int) response.getData().getAvgRates() ==2) {
            mIvStars.setBackgroundResource(R.drawable.star_2_img);
        }
        else if((int)response.getData().getAvgRates() ==3) {
            mIvStars.setBackgroundResource(R.drawable.star_3_img);
        }
        else if((int) response.getData().getAvgRates() ==4) {
            mIvStars.setBackgroundResource(R.drawable.star_4_img);
        }
        else if((int) response.getData().getAvgRates() ==5) {
            mIvStars.setBackgroundResource(R.drawable.star_5_img);
        }
        mTvReviewCount.setText(String.valueOf(response.getData().getReviews())+" reviews");
        if(response.getData().getWriter() != null) {
            drawerCircleImage(httpChange(response.getData().getWriter().getProfile()));
            mTvNickname.setText(response.getData().getWriter().getNickname()+" 님");
            mTvPostingCount.setText("포스팅 "+String.valueOf(response.getData().getWriter().getPosting()));
        }
        else {
            drawerInstagram();

            mTvNickname.setText("To Instagram");
            mTvPostingCount.setText("* 선택 시 해당 게시글로 이동합니다.");
            mPBManner.setProgress(100);
            mTvProgressBarCount.setText("100 %");
        }
    }

    @Override
    public void validateDetailPostFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    @Override
    public void validateRateSuccess(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateRateFailure(String message) {
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

    public void drawerInstagram() {
        mIvFirst.setBackgroundResource(0);
        mIvSecond.setBackgroundResource(0);
        mIvNickname.setBackgroundResource(R.drawable.pink_instagram_img);
    }
}
