package com.capstone.android.instatour.src.main.interfaces;

import java.util.ArrayList;

public interface MainActivityView {

    void validateSuccess(ArrayList<String> list);

    void validateFailure(String message);
}
