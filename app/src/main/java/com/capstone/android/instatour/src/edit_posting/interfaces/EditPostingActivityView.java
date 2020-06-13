package com.capstone.android.instatour.src.edit_posting.interfaces;

import com.capstone.android.instatour.src.edit_posting.models.BasicResponse;
import com.capstone.android.instatour.src.main.models.MainUserResponse;

import java.util.ArrayList;

public interface EditPostingActivityView {

    void validateSuccess(ArrayList<String> list);
    void validateFailure(String message);

    void validateUserSuccess(MainUserResponse list);
    void validateUserFailure(String message);

    void validatePostingSuccess(BasicResponse list);
    void validatePostingFailure(String message);
}
