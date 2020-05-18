package com.capstone.android.instatour.src.splash.interfaces;

import java.util.ArrayList;

public interface SplashActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
