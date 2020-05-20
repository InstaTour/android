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

public class Signup2Fragment extends Fragment {
    private EditText mEtPw1, mEtPw2;
    private SignupInterface signupInterface;
    public Signup2Fragment(SignupInterface signupInterface) {
        this.signupInterface = signupInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup2, container, false);

        setComponentView(view);

        view.findViewById(R.id.fragment_signup2_next_btn).setOnClickListener(view1 -> {
            if(checkValidation()) {
                SignupActivity.mViewPagerSignUp.setCurrentItem(2);
                signupInterface.password(mEtPw1.getText().toString());
            }
        });

        return  view;
    }



    public void setComponentView(View view) {
        mEtPw1 = view.findViewById(R.id.fragment_signup2_pw1_et);
        mEtPw2 = view.findViewById(R.id.fragment_signup2_pw2_et);
    }

    public boolean checkValidation() {
        if(mEtPw1.getText().length() == 0) {
            Toast.makeText(getActivity(), "비밀번호를 입력 해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!mEtPw1.getText().toString().equals(mEtPw2.getText().toString())) {
            Toast.makeText(getActivity(), "비밀번호를 같게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }


}
