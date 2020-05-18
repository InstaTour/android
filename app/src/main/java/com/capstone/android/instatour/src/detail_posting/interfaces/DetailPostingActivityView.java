package com.capstone.android.instatour.src.detail_posting.interfaces;

import java.util.ArrayList;

public interface DetailPostingActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
