package com.capstone.android.instatour.src.signup.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
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
import com.capstone.android.instatour.R;
import com.capstone.android.instatour.src.edit_posting.adapters.UriImageAdapter;
import com.capstone.android.instatour.src.edit_posting.dialogs.ImageSelectDialog;
import com.capstone.android.instatour.src.edit_posting.interfaces.DialogImageSelectInterface;
import com.capstone.android.instatour.src.main.MainActivity;
import com.capstone.android.instatour.src.signup.interfaces.SignupInterface;
import com.capstone.android.instatour.utils.Utils;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Signup3Fragment extends Fragment {
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
    private ImageView mIvPicture;
    private SignupInterface signupInterface;

    public Signup3Fragment(SignupInterface signupInterface) {
        this.signupInterface = signupInterface;
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup3, container, false);

        setComponentView(view);

        setListener(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        checkPermissions();
    }

    public void setListener(View view) {
        mIvPicture.setOnClickListener(view1 -> {
            if (mMode == BEFORE_IMAGE || mMode == AFTER_IMAGE) {
                ImageSelectDialog dialog = new ImageSelectDialog(activity, mCameraInterface);
            } else if (mMode == AFTER_SEVER_UPLOAD) {
                startActivity(new Intent(activity, MainActivity.class));
                // I don't know
            }
        });

        view.findViewById(R.id.fragment_signup3_next_btn).setOnClickListener(view12 -> uploadFileToS3());
    }

    public void setComponentView(View view) {
        mIvPicture = view.findViewById(R.id.fragment_signup3_image_iv);
    }


    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            if(activity == null) {
               activity = getActivity();
            }
            result = ContextCompat.checkSelfPermission(activity, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
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
                                Toast.makeText(activity, R.string.permission_string, Toast.LENGTH_SHORT).show();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(activity, R.string.permission_string, Toast.LENGTH_SHORT).show();
                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(activity, R.string.permission_string, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(activity, R.string.permission_string, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Log.i("SDVSVDSD", e.getMessage());
            Toast.makeText(activity, R.string.review_edit_image_error, Toast.LENGTH_SHORT).show();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(activity,
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            Toast.makeText(activity, "이미지 처리 오류! 다시 시도해주세요1.", Toast.LENGTH_SHORT).show();

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

           cropImage();
        }
        // set uri in selecting album and start cropping

        else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
        }
        // set uri in camera and start cropping

        else if (requestCode == CROP_FROM_CAMERA) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), savingUri);
                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 800, 800);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                // exract bitmap -> saving uri(crop image)

                mIvPicture.setImageBitmap(thumbImage);

                mMode = AFTER_IMAGE;
            } catch (Exception e) {
            }
        }
        // this code uses crop intent but not use this code. becuase crop intent can not use some API so uses below code

        else if (requestCode == UCrop.REQUEST_CROP) {
            try {
                savingUri = UCrop.getOutput(data);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), savingUri);
                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 800, 800);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);

                mIvPicture.setImageBitmap(thumbImage);

                mMode = AFTER_IMAGE;
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
                Log.i("VDSDSVSDV", e.getMessage());
                Toast.makeText(activity, "이미지 처리 오류! 다시 시도해주세요.2", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        savingUri = Uri.fromFile(tmpFile);

        UCrop.of(photoUri, savingUri)
                .withAspectRatio(16, 16)
                .start(activity);
    }

    public Uri getRealPathFromURI(Uri contentUri) {
        if (contentUri.getPath().startsWith("/storage")) {
            return Uri.parse("file://" + contentUri.getPath());
        }
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = {MediaStore.Files.FileColumns.DATA};
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = activity.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                columns,
                selection,
                null,
                null);
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) {
                return Uri.parse("file://" + cursor.getString(columnIndex));
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    // content uri parse to real uri path

    public void uploadFileToS3() {
        if(savingUri == null) {
            signupInterface.imgUrl(null);
            return;
        }
        AWSCredentials awsCredentials = new BasicAWSCredentials(getString(R.string.ACCESS_KEY), getString(R.string.SECRET_KEY));
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));
        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(activity).build();
        TransferNetworkLossHandler.getInstance(activity);

        TransferObserver uploadObserver = null;
        String time = Utils.getNowMilliSecond();
        uploadObserver = transferUtility.upload("instatour-image/profile", time + String.valueOf("profile") + ".png", new File(savingUri.getPath()), CannedAccessControlList.PublicRead);

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d(TAG, "onStateChanged: " + id + ", " + state.toString());

                if (state == TransferState.COMPLETED) {
                    String url = s3Client.getResourceUrl("instatour-image/profile", time + String.valueOf("profile") + ".png");
                    signupInterface.imgUrl(url);
                }

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;
                Log.d(TAG, "ID:" + id + " bytesCurrent: " + bytesCurrent + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        });
    }
}
