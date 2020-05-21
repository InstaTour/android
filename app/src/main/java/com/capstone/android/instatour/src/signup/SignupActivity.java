package com.capstone.android.instatour.src.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.capstone.android.instatour.src.signup.adapters.SignupFragmentAdapter;
import com.capstone.android.instatour.src.signup.interfaces.SignupInterface;
import com.google.android.material.tabs.TabLayout;

import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.main.MainActivity;

public class SignupActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private String eamil, name, password, url;
    private SignupInterface mSignupInterface = new SignupInterface() {
        @Override
        public void email(String text) {
            eamil = text;
        }

        @Override
        public void name(String text) {
            name = text;
        }

        @Override
        public void password(String text) {
            password = text;
            mTabLayout.getTabAt(1).select();
        }

        @Override
        public void imgUrl(String text) {
            url = text;

            signup();
        }
    };
    public static NonSwipeableViewPager mViewPagerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        initAdapter();
        initTab();
    }

    public void initAdapter() {
        SignupFragmentAdapter mFragmentAdapter = new SignupFragmentAdapter(getSupportFragmentManager(), mSignupInterface);
        mViewPagerSignUp.setAdapter(mFragmentAdapter);
        mViewPagerSignUp.setScrollDurationFactor(3);
    }

    public void initTab() {
        mTabLayout.addTab(mTabLayout.newTab().setText("기본정보입력"));
        mTabLayout.addTab(mTabLayout.newTab().setText("캐릭터선택"));

        LinearLayout tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_back_iv:
                finish();
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                break;
        }
    }

    @Override
    public void initViews() {
        mTabLayout = findViewById(R.id.signup_tablayout);
        mViewPagerSignUp = findViewById(R.id.signup_viewpager);
    }

    @Override
    public void onBackPressed() {
        int index = mViewPagerSignUp.getCurrentItem();

        if(index ==0) {
            finish();
            overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
        }
        else {
            if(index == 2){
                mTabLayout.getTabAt(0).select();
            }
            mViewPagerSignUp.setCurrentItem(index-1, true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void signup() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
    }
}
