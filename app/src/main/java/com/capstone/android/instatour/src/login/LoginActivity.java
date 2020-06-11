package com.capstone.android.instatour.src.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.ApplicationClass;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.main.MainActivity;
import com.capstone.android.instatour.src.signup.SignupActivity;

import static com.capstone.android.instatour.src.ApplicationClass.X_ACCESS_TOKEN;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Activity activity;
    private EditText mEtEmail, mEtPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initPw();
        init();
    }

    public void init() {
        activity = this;
    }

    public void initPw() {
        mEtPW.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

               signIn();

                return true;
            }
            return false;
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                signIn();
                break;
            case R.id.login_to_signup_tv:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                break;
            case R.id.login_to_search_tv:
                Toast.makeText(activity, "서비스 기획 중입니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void initViews() {
        mEtEmail = findViewById(R.id.login_email_et);
        mEtPW = findViewById(R.id.login_pw_et);
    }

    public void signIn() {
        showProgressDialog();
        AWSMobileClient.getInstance().signIn(mEtEmail.getText().toString(), mEtPW.getText().toString(), null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(() -> {
                    hideProgressDialog();

                    switch (signInResult.getSignInState()) {
                        case DONE:
                            String jwt = null;
                            try {
                                jwt = AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            SharedPreferences mSharedPreferences1 = getSharedPreferences(ApplicationClass.TAG, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = mSharedPreferences1.edit();
                            editor1.putString(X_ACCESS_TOKEN, jwt);
                            editor1.commit();

                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                            finish();
                            break;

                        default:
                            SharedPreferences mSharedPreferences = getSharedPreferences(ApplicationClass.TAG, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.remove(X_ACCESS_TOKEN);

                            editor.commit();
                            showCustomToast("Unsupported sign-in confirmation: " + signInResult.getSignInState());

                            Toast.makeText(activity, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                hideProgressDialog();
                Log.e("LoginError", e.getMessage());
            }
        });
    }
}
