package com.capstone.android.instatour.src.login.interfaces;

import java.util.ArrayList;

public interface LoginActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
