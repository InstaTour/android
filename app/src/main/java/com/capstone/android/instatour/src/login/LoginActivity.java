package com.capstone.android.instatour.src.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.main.MainActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Activity activity;
    private EditText mEtEmail, mEtPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initPw();

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
                break;
            case R.id.login_to_signup_tv:

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
}
