package com.capstone.android.instatour.src.detail_posting.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.detail_posting.interfaces.DialogRatingInterface;

public class DetailPostingRatingDialog implements View.OnClickListener{

    private Context mContext;
    private TextView mTvNo, mTvYes;
    private DialogRatingInterface mInetface;
    private RatingBar mRB;
    private  float rate;

    public Dialog mDialog;

    public DetailPostingRatingDialog(Context context, float rating, DialogRatingInterface tmpInterface) {
        mContext = context;
        mDialog = new Dialog(mContext);
        this.rate = rating;

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_posting_rating);

        mRB = mDialog.findViewById(R.id.dialog_posting_ratingbar);
        mTvNo = mDialog.findViewById(R.id.dialog_posting_rating_no_tv);
        mTvYes = mDialog.findViewById(R.id.dialog_posting_rating_yes_tv);

        mTvNo.setOnClickListener(this);
        mTvYes.setOnClickListener(this);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        mInetface = tmpInterface;

        mRB.setRating(rating);
        mRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_posting_rating_yes_tv:
                mDialog.dismiss();
                mInetface.rating(rate);
                break;
            case R.id.dialog_posting_rating_no_tv:
                mDialog.dismiss();
                break;
        }
    }
}

