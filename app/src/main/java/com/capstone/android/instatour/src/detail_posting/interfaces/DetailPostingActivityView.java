package com.capstone.android.instatour.src.detail_posting.interfaces;

import com.capstone.android.instatour.src.detail_posting.models.DetailPostResponse;

import java.util.ArrayList;

public interface DetailPostingActivityView {
    void validateDetailPostSuccess(DetailPostResponse response);
    void validateDetailPostFailure(String message);

    void validateRateSuccess(String message);
    void validateRateFailure(String message);
}
