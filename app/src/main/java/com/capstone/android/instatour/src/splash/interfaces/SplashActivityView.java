package com.capstone.android.instatour.src.splash.interfaces;

import com.capstone.android.instatour.src.splash.models.SplashResponse;
import com.capstone.android.instatour.src.splash.models.UserResponse;

import java.util.ArrayList;

public interface SplashActivityView {

    void validateSuccess(SplashResponse splashResponse);

    void validateFailure(String message);
}
