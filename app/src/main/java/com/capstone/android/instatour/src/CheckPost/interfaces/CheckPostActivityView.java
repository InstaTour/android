package com.capstone.android.instatour.src.CheckPost.interfaces;

import com.capstone.android.instatour.src.CheckPost.models.CheckResponse;

public interface CheckPostActivityView {

    void validateSuccess(CheckResponse response);
    void validateFailure(String message);
}
