package com.capstone.android.instatour.src.edit_posting.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.edit_posting.interfaces.DialogImageSelectInterface;

public class ImageSelectDialog implements View.OnClickListener{

    private Context mContext;
    private TextView mTvAlbum, mTvCamera;
    private DialogImageSelectInterface mInetface;

    public Dialog mDialog;

    public ImageSelectDialog(Context context, DialogImageSelectInterface tmpInterface) {
        mContext = context;
        mDialog = new Dialog(mContext);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_image_select);

        mTvAlbum = mDialog.findViewById(R.id.dialog_album_tv);
        mTvCamera = mDialog.findViewById(R.id.dialog_camera_tv);

        mTvAlbum.setOnClickListener(this);
        mTvCamera.setOnClickListener(this);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        this.mInetface = tmpInterface;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_album_tv:
                mInetface.album();
                mDialog.dismiss();
                break;
            case R.id.dialog_camera_tv:
                mInetface.camera();
                mDialog.dismiss();
                break;
        }
    }
}

