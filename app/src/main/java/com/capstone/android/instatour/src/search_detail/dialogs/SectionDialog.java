package com.capstone.android.instatour.src.search_detail.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.search_detail.interfaces.DialogSectionInterface;

public class SectionDialog implements View.OnClickListener{

    private Context mContext;
    private TextView mTvAll, mTvLocation, mTvFood, mTvFinish;
    private DialogSectionInterface mInetface;
    private Dialog mDialog;
    private  String mStrStatus = null;

    public SectionDialog(Context context, DialogSectionInterface tmpInterface, String status) {
        mContext = context;
        mDialog = new Dialog(mContext);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_posting_section);

        mTvAll = mDialog.findViewById(R.id.dialog_posting_section_all_tv);
        mTvLocation = mDialog.findViewById(R.id.dialog_posting_section_location_tv);
        mTvFood = mDialog.findViewById(R.id.dialog_posting_section_food_tv);
        mTvFinish = mDialog.findViewById(R.id.dialog_posting_section_finish_tv);

        mTvAll.setOnClickListener(this);
        mTvLocation.setOnClickListener(this);
        mTvFood.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        mInetface = tmpInterface;
        mStrStatus = status;

        if(status.equals("SEC_ALL")) {
            mTvAll.setTextColor(mContext.getResources().getColorStateList(R.color.colorWhite));
            mTvAll.setBackgroundResource(R.drawable.pink_click_radius_background);
        }
        else if(status.equals("SEC_FOOD")) {
            mTvFood.setTextColor(mContext.getResources().getColorStateList(R.color.colorWhite));
            mTvFood.setBackgroundResource(R.drawable.pink_click_radius_background);
        }
        else if(status.equals("SEC_SIGHTS")) {
            mTvLocation.setTextColor(mContext.getResources().getColorStateList(R.color.colorWhite));
            mTvLocation.setBackgroundResource(R.drawable.pink_click_radius_background);
        }
    }

    @Override
    public void onClick(View v) {
        changeEmptyText();
        switch (v.getId()) {
            case R.id.dialog_posting_section_all_tv:
                mStrStatus = "SEC_ALL";

                mTvAll.setTextColor(mContext.getResources().getColorStateList(R.color.colorWhite));
                mTvAll.setBackgroundResource(R.drawable.pink_click_radius_background);
                break;
            case R.id.dialog_posting_section_location_tv:
                mStrStatus = "SEC_SIGHTS";

                mTvLocation.setTextColor(mContext.getResources().getColorStateList(R.color.colorWhite));
                mTvLocation.setBackgroundResource(R.drawable.pink_click_radius_background);
                break;
            case R.id.dialog_posting_section_food_tv:
                mStrStatus = "SEC_FOOD";

                mTvFood.setTextColor(mContext.getResources().getColorStateList(R.color.colorWhite));
                mTvFood.setBackgroundResource(R.drawable.pink_click_radius_background);
                break;
            case R.id.dialog_posting_section_finish_tv:
                mInetface.click(mStrStatus);
                mDialog.dismiss();
                break;
        }
    }

    public void changeEmptyText() {
        mTvAll.setTextColor(mContext.getResources().getColorStateList(R.color.colorViolet));
        mTvAll.setBackgroundResource(R.drawable.pink_non_click_radius_background);

        mTvLocation.setTextColor(mContext.getResources().getColorStateList(R.color.colorViolet));
        mTvLocation.setBackgroundResource(R.drawable.pink_non_click_radius_background);

        mTvFood.setTextColor(mContext.getResources().getColorStateList(R.color.colorViolet));
        mTvFood.setBackgroundResource(R.drawable.pink_non_click_radius_background);
    }
}

