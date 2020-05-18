package com.capstone.android.instatour.src.search_detail.interfaces;

import java.util.ArrayList;

public interface SearchDetailActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
