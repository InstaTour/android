package com.capstone.android.instatour.src.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.main.MainActivity;

public class SplashActivity extends BaseActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                SharedPreferences pref = getSharedPreferences(InstaTourApp.TAG, Context.MODE_PRIVATE);
//                String jwt = pref.getString(X_ACCESS_TOKEN, null);
//                if (jwt != null) {
//                    login();
//                }
//                else {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    // set version

//    public void login() {
//
//        JsonObject params = new JsonObject();
//
//        MoariApp.getRetrofitMethod(getApplicationContext()).postLogin(RequestBody.create(MEDIA_TYPE_JSON, params.toString()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<LoginResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mCompositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(LoginResponse res) {
//
//                        if (res.getCode() == 200) {
//                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                        // success auto login
//
//                        else if (res.getCode() == 403) {
//                            SharedPreferences mSharedPreferences = SplashActivity.this.getSharedPreferences(MoariApp.TAG, Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = mSharedPreferences.edit();
//                            editor.remove(X_ACCESS_TOKEN);
//                            editor.commit();
//                            // remove token when token error
//
//                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else {
//                            Toast.makeText(SplashActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplicationContext(), catchAllThrowable(getApplicationContext(), e), Toast.LENGTH_SHORT).show();
//                        dismissProgressDialog();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        dismissProgressDialog();
//                    }
//                });
//    }
    // auto login

    @Override
    public void onClick(View v) {
    }
}
