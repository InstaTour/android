package com.capstone.android.instatour.src.edit_posting;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.bumptech.glide.Glide;
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.BaseActivity;
import com.capstone.android.instatour.src.edit_posting.adapters.UriImageAdapter;
import com.capstone.android.instatour.src.edit_posting.dialogs.ImageSelectDialog;
import com.capstone.android.instatour.src.edit_posting.interfaces.DialogImageSelectInterface;
import com.capstone.android.instatour.src.edit_posting.interfaces.EditPostingActivityView;
import com.capstone.android.instatour.src.edit_posting.models.BasicResponse;
import com.capstone.android.instatour.src.main.MainActivity;
import com.capstone.android.instatour.src.main.models.MainUserResponse;
import com.capstone.android.instatour.utils.Utils;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.capstone.android.instatour.src.ApplicationClass.httpChange;

public class EditPostingActivity extends BaseActivity implements EditPostingActivityView {

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; // permission check variable
    private static final int MULTIPLE_PERMISSIONS = 101; //use callback function after permession check
    private static final int PICK_FROM_CAMERA = 1; // bring picture using camera
    private static final int PICK_FROM_ALBUM = 2; // bring album using camera
    private static final int CROP_FROM_CAMERA = 3; // using crop
    private static final int BEFORE_IMAGE = 4; // using crop
    private static final int AFTER_IMAGE = 5; // using crop
    private static final int AFTER_SEVER_UPLOAD = 6; // using crop
    private int mMode = BEFORE_IMAGE; // use in onActivityResult function for distinguishing flag
    private Activity activity;
    private Uri photoUri, savingUri; // first picture, crop picture
    private static final String TAG = MainActivity.class.getSimpleName();
    private File tmpFile;
    private UriImageAdapter uriImageAdapter;
    private ViewPager2 mVP2Image;
    private EditText mEtContent;
    private  ArrayList<String> imgUrlList = new ArrayList<>();

    private TextView mTvLocation, mTvSection, mTvDate, mTvNickname;
    private ImageView mIvFirst, mIvSecond, mIvName;
    private String section;
    private int uploadFileSize=0;
    private String time = "";

    int uploadStatus =0;

    private DialogImageSelectInterface mCameraInterface = new DialogImageSelectInterface() {
        @Override
        public void album() {
            goToAlbum();
        }

        @Override
        public void camera() {
            takePhoto();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posting);

        initViews();
        checkPermissions();

        init();
        initAdapter();

        getUser();
    }

    public void initAdapter() {
        uriImageAdapter = new UriImageAdapter(this);
        mVP2Image.setAdapter(uriImageAdapter);
        mVP2Image.setOrientation(mVP2Image.ORIENTATION_HORIZONTAL);
    }

    public void init() {
        activity = this;
        mTvLocation.setText(getIntent().getStringExtra("location"));
        section = getIntent().getStringExtra("section");
        if(section.equals("SEC_ALL")) {
            mTvSection.setText("전체");
        }
        else if(section.equals("SEC_FOOD")) {
            mTvSection.setText("맛집");
        }
        else if(section.equals("SEC_SIGHTS")) {
            mTvSection.setText("관광지");
        }
        mTvSection.setText(getIntent().getStringExtra("section"));
        mTvDate.setText(Utils.getNowBarTime());
    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(this, R.string.permission_string, Toast.LENGTH_SHORT).show();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(this, R.string.permission_string, Toast.LENGTH_SHORT).show();
                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(this, R.string.permission_string, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, R.string.permission_string, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void getUser() {
        showProgressDialog();

        final EditPostingService mainService = new EditPostingService(this);
        mainService.getUser();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_posting_picture_iv:
                if (mMode == BEFORE_IMAGE || mMode == AFTER_IMAGE) {
                    ImageSelectDialog dialog = new ImageSelectDialog(activity, mCameraInterface);
                } else if (mMode == AFTER_SEVER_UPLOAD) {
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    // I don't know
                }
                break;
            case R.id.edit_posting_finish_btn:
                uploadFileToS3();
                break;
            case R.id.edit_posting_back_iv:
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
        }
    }

    @Override
    public void initViews() {
        mVP2Image = findViewById(R.id.edit_posting_vp2);
        mTvLocation = findViewById(R.id.edit_posting_location_tv);
        mTvSection = findViewById(R.id.edit_posting_section_tv);
        mTvDate = findViewById(R.id.edit_posting_date_tv);
        mTvNickname = findViewById(R.id.edit_posting_nickname_tv);
        mIvFirst = findViewById(R.id.edit_posting_first_iv);
        mIvSecond = findViewById(R.id.edit_posting_second_iv);
        mIvName = findViewById(R.id.edit_posting_user_iv);
        mEtContent = findViewById(R.id.edit_posting_content_et);
    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(activity, R.string.review_edit_image_error, Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
            finish();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(this,
                    "com.capstone.android.instatour.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "IP" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/instatour/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 0) {
            return;
        }
        // when press back button in album

        if (requestCode == 1 && resultCode == 0) {
            return;
        }
        // when press back selecting camera

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();

            if (tmpFile != null) {
                if (tmpFile.exists()) {
                    if (tmpFile.delete()) {
                        Log.e(TAG, tmpFile.getAbsolutePath() + " 삭제 성공");
                        tmpFile = null;
                    }
                }
            }
        }
        // exception handling

        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                return;
            }
            photoUri = data.getData();

            uriImageAdapter.clearData();
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    uriImageAdapter.addData(getRealPathFromURI(uri));
                }
                uriImageAdapter.notifyDataSetChanged();
            } else {
                uriImageAdapter.addData(getRealPathFromURI(photoUri));
                uriImageAdapter.notifyDataSetChanged();
            }
        }
        // set uri in selecting album and start cropping

        else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
        }
        // set uri in camera and start cropping

        else if (requestCode == CROP_FROM_CAMERA) {
            try {
                uriImageAdapter.clearData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), savingUri);
                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 800, 800);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                // exract bitmap -> saving uri(crop image)

                mMode = AFTER_IMAGE;

                uriImageAdapter.addData(savingUri);
                uriImageAdapter.notifyDataSetChanged();
                Log.i("TESTSTAR3", String.valueOf(savingUri));
            } catch (Exception e) {
            }
        }
        // this code uses crop intent but not use this code. becuase crop intent can not use some API so uses below code

        else if (requestCode == UCrop.REQUEST_CROP) {
            try {
                savingUri = UCrop.getOutput(data);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), savingUri);
                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 800, 800);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);

                uriImageAdapter.addData(savingUri);
                uriImageAdapter.notifyDataSetChanged();

                mMode = AFTER_IMAGE;
                Log.i("TESTSTAR3", String.valueOf(savingUri));
            } catch (Exception e) {
            }
        }
        // use this code.
    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void cropImage() {
        if (tmpFile == null) {
            try {
                tmpFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
                finish();
                e.printStackTrace();
            }
        }

        savingUri = Uri.fromFile(tmpFile);

        UCrop.of(photoUri, savingUri)
                .withAspectRatio(16, 16)
                .start(activity);
        // important code

    }

    public Uri getRealPathFromURI (Uri contentUri) {
        if (contentUri.getPath().startsWith("/storage")) {
            return Uri.parse("file://"+contentUri.getPath());
        }
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = { MediaStore.Files.FileColumns.DATA };
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"),
                columns,
                selection,
                null,
                null);
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) {
                return Uri.parse("file://"+cursor.getString(columnIndex));
            }
        }
        finally {
            cursor.close();
        }
        return null;
    }
    // content uri parse to real uri path

    public void uploadFileToS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(getString(R.string.ACCESS_KEY), getString(R.string.SECRET_KEY));
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));
        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(this).build();
        TransferNetworkLossHandler.getInstance(this);

        TransferObserver uploadObserver = null;
        time = Utils.getNowMilliSecond();

        uploadFileSize = uriImageAdapter.getListData().size();

        Log.i("SDVsdvsd", String.valueOf(uriImageAdapter.getListData().size()));
        for(int i=0;i<uploadFileSize;i++){
            uploadObserver = transferUtility.upload("instatour-image/upload", time+String.valueOf(i)+".png", new File(uriImageAdapter.getListData().get(i).getPath()), CannedAccessControlList.PublicRead);
        }

        if(uriImageAdapter.getListData().size() ==0) {
            apiUpload();
            return;
        }
        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d(TAG, "onStateChanged: " + id + ", " + state.toString());

                if(state == TransferState.COMPLETED) {
                    for(int i=0;i<uploadFileSize;i++){
                        imgUrlList.add(s3Client.getResourceUrl("instatour-image/upload", time+String.valueOf(i)+".png"));
                    }

//                    String url = s3Client.getResourceUrl("instatour-image/profile", time + String.valueOf("profile") + ".png");

                    apiUpload();
                }

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;
                Log.d(TAG, "ID:" + id + " bytesCurrent: " + bytesCurrent + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        });
    }

    public void apiUpload() {
        showProgressDialog();

        final EditPostingService mainService = new EditPostingService(this);
        try {
            mainService.postPoting(mTvLocation.getText().toString(), section, mEtContent.getText().toString(),  imgUrlList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateSuccess(ArrayList<String> list) {
        hideProgressDialog();
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    @Override
    public void validateUserSuccess(MainUserResponse user) {
        hideProgressDialog();

        if(user.getCode() != 200) {
            Toast.makeText(this, user.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(user.getData() != null) {
            mTvNickname.setText(user.getData().getUser().getNickname());
        }

        String url =  user.getData().getUser().getProfile();

        Glide.with(activity)
                .load(R.drawable.main_color_circle_img)
                .fitCenter()
                .circleCrop()
                .into(mIvFirst);

        Glide.with(activity)
                .load(R.drawable.white_background)
                .fitCenter()
                .circleCrop()
                .into(mIvSecond);

        if(url.equals("null")) {
            Glide.with(activity)
                    .load(R.drawable.instatour_logo_img)
                    .fitCenter()
                    .circleCrop()
                    .into(mIvName);
        }
        else {
            Glide.with(activity)
                    .load(httpChange(url))
                    .fitCenter()
                    .circleCrop()
                    .into(mIvName);
        }
    }

    @Override
    public void validateUserFailure(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validatePostingSuccess(BasicResponse list) {
        hideProgressDialog();
        Log.i("Vsdvdsdasdsd", String.valueOf(list.getCode()));
        System.out.println(list.getMessage());
        overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
        activity.finish();
    }

    @Override
    public void validatePostingFailure(String message) {
        hideProgressDialog();

        overridePendingTransition(R.anim.amin_slide_in_right, R.anim.amin_slide_out_left);
        activity.finish();
    }
}
