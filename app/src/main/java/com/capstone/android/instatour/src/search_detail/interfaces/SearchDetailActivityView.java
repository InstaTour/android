package com.capstone.android.instatour.src.search_detail.interfaces;

import com.capstone.android.instatour.src.search_detail.models.BasicResponse;
import com.capstone.android.instatour.src.search_detail.models.SearchDetailResponse;

import java.util.ArrayList;

public interface SearchDetailActivityView {

    void validateSuccess(SearchDetailResponse response);
    void validateFailure(String message);

    void validateHeartSuccess(String message);
    void validateHeartFailure(String message);
}
