package com.capstone.android.instatour.src.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.main.MainActivity;
import com.capstone.android.instatour.src.signup.SignupActivity;

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
        
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );
    }

    public void init() {
        activity = this;
    }

    public void initPw() {
        mEtPW.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);

                    return true;
                }
                return false;
            }
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
        AWSMobileClient.getInstance().signIn(mEtEmail.getText().toString(), mEtPW.getText().toString(), null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("LOGLOG", "Sign-in callback state: " + signInResult.getSignInState());
                        switch (signInResult.getSignInState()) {
                            case DONE:
                                makeToast("Sign-in done.");
                                Intent intent = new Intent(activity, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
                                break;
                            case SMS_MFA:
                                makeToast("Please confirm sign-in with SMS.");
                                break;
                            case NEW_PASSWORD_REQUIRED:
                                makeToast("Please confirm sign-in with new password.");
                                break;
                            default:
                                makeToast("Unsupported sign-in confirmation: " + signInResult.getSignInState());
                                break;
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e("LOGLOG", "Sign-in error", e);
            }
        });
    }

    public void makeToast(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
}
