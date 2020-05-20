package com.capstone.android.instatour.src.signup.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.capstone.android.instatour.src.signup.fragments.Signup1Fragment;
import com.capstone.android.instatour.src.signup.fragments.Signup2Fragment;
import com.capstone.android.instatour.src.signup.fragments.Signup3Fragment;
import com.capstone.android.instatour.src.signup.interfaces.SignupInterface;

public class SignupFragmentAdapter extends FragmentStatePagerAdapter {
    private int mPageCount=3;
    private  SignupInterface signupInterface;

    public SignupFragmentAdapter(FragmentManager fm, SignupInterface signupInterface) {
        super(fm);
        this.signupInterface = signupInterface;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Signup1Fragment(signupInterface);
            case 1:
                return new Signup2Fragment(signupInterface);
            case 2:
                return new Signup3Fragment(signupInterface);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
