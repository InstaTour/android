package com.capstone.android.instatour.src.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;


import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.capstone.android.instatour.src.ApplicationClass;
import com.capstone.android.instatour.src.login.LoginActivity;
import com.capstone.android.instatour.src.main.MainActivity;
import com.capstone.android.instatour.src.search.SearchActivity;
import com.capstone.android.instatour.src.signup.adapters.SignupFragmentAdapter;
import com.capstone.android.instatour.src.signup.interfaces.SignupInterface;
import com.google.android.material.tabs.TabLayout;

import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import static com.capstone.android.instatour.src.ApplicationClass.X_ACCESS_TOKEN;

public class SignupActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private String ID, email, name, password, url;
    private  Activity activity;

    private SignupInterface mSignupInterface = new SignupInterface() {
        @Override
        public void id(String text) {
            ID = text;
        }

        @Override
        public void email(String text) {
            email = text;
        }

        @Override
        public void name(String text) {
            name = text;
            Log.i("SDVSVDvdS", name);
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

        activity = this;
    }

    public void initAdapter() {
        SignupFragmentAdapter mFragmentAdapter = new SignupFragmentAdapter(getSupportFragmentManager(), mSignupInterface);
        mViewPagerSignUp.setAdapter(mFragmentAdapter);
        mViewPagerSignUp.setScrollDurationFactor(3);
    }

    public void initTab() {
        mTabLayout.addTab(mTabLayout.newTab().setText("기본정보입력"));
        mTabLayout.addTab(mTabLayout.newTab().setText("캐릭터선택"));

        LinearLayout tabStrip = ((LinearLayout) mTabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_back_iv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
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

        if (index == 0) {
            overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
            finish();
        } else {
            if (index == 2) {
                mTabLayout.getTabAt(0).select();
            }
            mViewPagerSignUp.setCurrentItem(index - 1, true);
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
        showProgressDialog();

        final String username = ID;
        final String pw = password;
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("nickname", name);
        if(url == null) {
            url = "null";
        }
        attributes.put("profile", url);

        AWSMobileClient.getInstance().signUp(username, pw, attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("AWSAWSLOGIN", "Sign-up callback state: " + signUpResult.getConfirmationState());
                        if (!signUpResult.getConfirmationState()) {
                            final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                            Toast.makeText(SignupActivity.this, details.getDestination(), Toast.LENGTH_SHORT).show();
                        } else {
                            String jwt = null;
                            try {
                                jwt = AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
                                Log.i("SIGNUPJWT", jwt);
                            } catch (Exception e) {
                                Log.i("SIGNUPEXCEPTION", e.getMessage());
                                e.printStackTrace();
                            }
                            SharedPreferences mSharedPreferences1 = getSharedPreferences(ApplicationClass.TAG, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = mSharedPreferences1.edit();
                            editor1.putString(X_ACCESS_TOKEN, jwt);
                            editor1.commit();

                            hideProgressDialog();

                            Toast.makeText(activity, "회원가입을 성공하였습니다", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(activity, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("AWSAWSLOGIN", "Sign-up error", e);
            }
        });
    }
}
