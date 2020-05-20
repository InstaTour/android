package com.capstone.android.instatour.src.signup.interfaces;

import java.util.ArrayList;

public interface SignupActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
