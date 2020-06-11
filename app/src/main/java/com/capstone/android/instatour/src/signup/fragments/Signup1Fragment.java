package com.capstone.android.instatour.src.signup.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.signup.SignupActivity;
import com.capstone.android.instatour.src.signup.interfaces.SignupInterface;

public class Signup1Fragment extends Fragment {
    private EditText mEtEmail, mEtName, mEtId;
    private  SignupInterface signupInterface;
    public Signup1Fragment(SignupInterface signupInterface) {
        this.signupInterface = signupInterface;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup1, container, false);

        setComponentView(view);

        view.findViewById(R.id.fragment_signup1_next_btn).setOnClickListener(view1 -> {
            if(checkValidation()) {
                SignupActivity.mViewPagerSignUp.setCurrentItem(1);
                signupInterface.id(mEtId.getText().toString());
                signupInterface.email(mEtEmail.getText().toString());
                signupInterface.name(mEtName.getText().toString());
            }
        });
        return  view;
    }



    public void setComponentView(View view) {
        mEtEmail = view.findViewById(R.id.fragment_signup1_email_et);
        mEtName = view.findViewById(R.id.fragment_signup1_name_et);
        mEtId = view.findViewById(R.id.fragment_signup1_id_et);
    }

    public boolean checkValidation() {
        if(mEtId.getText().length() == 0) {
            Toast.makeText(getActivity(), "아이드를 입려해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mEtEmail.getText().length() == 0) {
            Toast.makeText(getActivity(), "이메일을 입려해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mEtName.getText().length() ==0) {
            Toast.makeText(getActivity(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}
