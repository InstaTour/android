package com.capstone.android.instatour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.adapters.RecentSearchAdpater;
import com.capstone.android.instatour.datas.RecentData;


public class LoginActivity extends SuperActivity implements View.OnClickListener {

    private Activity activity;
    private EditText mEtEmail, mEtPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        activity = this;

        mEtPW.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Intent intent = new Intent(activity, SearchDetailActivity.class);
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
    void initViews() {
        mEtEmail = findViewById(R.id.login_email_et);
        mEtPW = findViewById(R.id.login_pw_et);
    }
}