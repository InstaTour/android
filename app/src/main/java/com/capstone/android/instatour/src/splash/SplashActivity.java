package com.capstone.android.instatour.src.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;
import com.amazonaws.mobile.client.results.SignInResult;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.ApplicationClass;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.login.LoginActivity;
import com.capstone.android.instatour.src.main.MainActivity;
import com.capstone.android.instatour.src.signup.SignupActivity;
import com.capstone.android.instatour.src.splash.interfaces.SplashActivityView;
import com.capstone.android.instatour.src.splash.models.SplashResponse;
import com.capstone.android.instatour.src.splash.models.UserResponse;

import java.util.ArrayList;

import static com.capstone.android.instatour.src.ApplicationClass.X_ACCESS_TOKEN;

public class SplashActivity extends BaseActivity implements SplashActivityView {

    private  Intent intent;
    private Activity activity;
    private  SharedPreferences pref;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;

        pref = getSharedPreferences(ApplicationClass.TAG, Context.MODE_PRIVATE);

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(() -> {
            autoLogin();
        }, 1000);
    }

    @Override
    public void onClick(View v) {
    }

    private void autoLogin() {
        final SplashService splashService = new SplashService(this);
        splashService.getTokenUser();
    }

    @Override
    public void validateSuccess(SplashResponse splashResponse) {
        if(splashResponse.getCode() == 200) {
            String jwt = null;
            try {
                jwt = AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor1 = pref.edit();
            editor1.putString(X_ACCESS_TOKEN, jwt);
            editor1.commit();

            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
            finish();
        }
        else {
            SharedPreferences.Editor editor = pref.edit();
            editor.remove(X_ACCESS_TOKEN);

            editor.commit();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.amin_slide_in_left, R.anim.amin_slide_out_right);
            finish();
        }
    }

    @Override
    public void validateFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
