package com.capstone.android.instatour.src.main.interfaces;

import com.capstone.android.instatour.src.main.models.MainTopClickResponse;
import com.capstone.android.instatour.src.main.models.MainTopReviewerResponse;
import com.capstone.android.instatour.src.main.models.MainUserResponse;

import java.util.ArrayList;

public interface MainActivityView {
    void validateUserSuccess(MainUserResponse list);
    void validateUserFailure(String message);

    void validateTopReviwerSuccess(MainTopReviewerResponse list);
    void validateTopReviwerFailure(String message);

    void validateTopClickMonthSuccess(MainTopClickResponse list);
    void validateTopClickMonthFailure(String message);

    void validateTopClickWeekSuccess(MainTopClickResponse list);
    void validateTopClickWeekFailure(String message);
}
