package com.capstone.android.instatour.src.edit_posting.interfaces;

import java.util.ArrayList;

public interface EditPostingActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
