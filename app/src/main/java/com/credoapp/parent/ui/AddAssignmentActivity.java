package com.credoapp.parent.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.credoapp.parent.R;
import com.credoapp.parent.adapter.AddingAssignmentsAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.addAssignment.AddAssignmentModel;
import com.credoapp.parent.model.addAssignment.AddAssignmentRequest;
import com.credoapp.parent.model.addAssignment.AddAssignmentResponse;
import com.credoapp.parent.model.addAssignment.GetAddedAssignmentImagesRequest;
import com.credoapp.parent.model.addAssignment.GetAddedAssignmentImagesResponse;
import com.credoapp.parent.retrofit.ITutorSource;

import com.google.gson.Gson;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAssignmentActivity extends AppCompatActivity {
    private static final String TAG = AddAssignmentActivity.class.getSimpleName();
    private final List<AddAssignmentModel> addAssignmentModels = new ArrayList<>();
    RecyclerView recyclerViewAddingAssignments;
    private MultipartBody.Part multiPartBody;
    public String assignmentId,studentId;
    TextView addButtonForAssignment;
    AddingAssignmentsAdapter addingAssignmentsAdapter;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<CropImageContractOptions> crop;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        studentId = preferences.getString(Constants.USER_IDD, studentId);

        assignmentId = Objects.requireNonNull(getIntent().getExtras()).getString("assignmentId");
        String viewSubmit = getIntent().getExtras().getString("viewSubmit");
        findViewById(R.id.backLayoutAddAssignment).setOnClickListener(view -> onBackPressed());
        recyclerViewAddingAssignments = findViewById(R.id.recyclerViewAddingAssignments);
        addButtonForAssignment = findViewById(R.id.addButtonForAssignment);
        TextView headerTextAddViewAssignment = findViewById(R.id.headerTextAddViewAssignment);
        registerReciver();
        assert viewSubmit != null;
        if (viewSubmit.equals("view")){
            headerTextAddViewAssignment.setText("ASSIGNMENT IMAGES");
            addButtonForAssignment.setVisibility(View.GONE);
        }else {
            headerTextAddViewAssignment.setText("UPLOAD ASSIGNMENT IMAGES");
            addButtonForAssignment.setVisibility(View.VISIBLE);
        }

        getImagesListSubmited();
        addButtonForAssignment.setOnClickListener(view -> {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
            if (result == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(AddAssignmentActivity.this, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(this::checkPermissions, 2000);
                checkPermission();
                crop.launch(new CropImageContractOptions(null, new CropImageOptions()));
            } else {

                //  Pix.start(AddAssignmentActivity.this, Options.init().setRequestCode(101));
            }
        });


        int numberOfColumns = 2;
        recyclerViewAddingAssignments.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        addingAssignmentsAdapter = new AddingAssignmentsAdapter(AddAssignmentActivity.this, addAssignmentModels);
        recyclerViewAddingAssignments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAddingAssignments.setAdapter(addingAssignmentsAdapter);

//        addingAssignmentsAdapter = new AddingAssignmentsAdapter(AddAssignmentActivity.this, addAssignmentModels);
//        recyclerViewAddingAssignments.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewAddingAssignments.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewAddingAssignments.setAdapter(addingAssignmentsAdapter);

    }

    private void registerReciver() {
        crop = registerForActivityResult(new CropImageContract(), result -> {
            Uri selectedFileUri = result.getUriContent();
            if (selectedFileUri != null) {
                File f = uriToFile(selectedFileUri, this);
                if (f != null) {

                    uploadFile(Uri.fromFile(f));

                }
            }

        });
    }
    private File uriToFile(Uri uri, Context context) {
        try {
            String fileName = getFileNameFromUri(uri, context);
            File file = new File(context.getCacheDir(), fileName);
            try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
                 FileOutputStream outputStream = new FileOutputStream(file)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getFileNameFromUri(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private void checkPermission() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            int MULTIPLE_PERMISSIONS = 10;
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
        }
    }
    private void getImagesListSubmited() {
        showLoadingDialog();
        GetAddedAssignmentImagesRequest request = new GetAddedAssignmentImagesRequest();
        request.setAssignmentId(assignmentId);
        request.setStudentId(studentId);

        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getAddedAssignmentImages(request).enqueue(new Callback<GetAddedAssignmentImagesResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetAddedAssignmentImagesResponse> call, @NonNull Response<GetAddedAssignmentImagesResponse> response) {
                    dismissLoadingDialog();
                    Log.d(TAG, "onResponse1: " + response);
                    if (response.isSuccessful()) {
                        getAddedAssignmentImagesResponse(Objects.requireNonNull(response.body()));
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<GetAddedAssignmentImagesResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                    Toast.makeText(getApplicationContext(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Constants.IntenetSettings(getApplicationContext());
        }





    }

    private void getAddedAssignmentImagesResponse(GetAddedAssignmentImagesResponse body) {
        if (body.getCode().equals("200")){
            addAssignmentModels.clear();
            addAssignmentModels.addAll(body.getResults());
            addingAssignmentsAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, body.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
//            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
//            Log.d(TAG, "onActivityResult: uriPaths        " + returnValue);
////            Uri uri = Uri.fromFile(new File(returnValue.get(0)));
////            Uri fileUrie = data.getData();
////            Log.d(TAG, "onActivityResult: fileUrie   " + fileUrie);
//            Uri myUri = Uri.parse(returnValue.get(returnValue.size() - 1));
//
//            File imgFile = new File(returnValue.get(returnValue.size() - 1));
//            Log.d(TAG, "onActivityResult: " + imgFile);
//
//            uploadFile(myUri);
////            if (imgFile.exists()) {
////                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                profile_icon.setImageBitmap(myBitmap);
////            }
//        }
//    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            Log.d(TAG, "onActivityResult: uriPaths        " + returnValue);
//            Uri uri = Uri.fromFile(new File(returnValue.get(0)));
//            Uri fileUrie = data.getData();
//            Log.d(TAG, "onActivityResult: fileUrie   " + fileUrie);
            Uri myUri = Uri.parse(returnValue.get(returnValue.size() - 1));


            File imgFile = new File(returnValue.get(returnValue.size() - 1));
            Log.d(TAG, "onActivityResult: " + imgFile);

//            if (imgFile.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                profile_icon.setImageBitmap(myBitmap);
//            }
            uploadFile(myUri);
        }
    }*/


    private void uploadFile(Uri myUri) {
        File file = new File(myUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
        multiPartBody = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        uploadImage();

    }

    private void uploadImage() {
        showLoadingDialog();
        AddAssignmentRequest request = new AddAssignmentRequest();
        request.setAssignmentId(assignmentId);
        request.setStudentId(studentId);
        String requestData = new Gson().toJson(request);
        Log.d(TAG, "submitStudentForm: " + requestData);
        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, requestData);

        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().addAssignmentImage(description, multiPartBody).enqueue(new Callback<AddAssignmentResponse>() {
                @Override
                public void onResponse(@NonNull Call<AddAssignmentResponse> call, @NonNull Response<AddAssignmentResponse> response) {
                    dismissLoadingDialog();
                    Log.d(TAG, "onResponse1: " + response);
                    if (response.isSuccessful()) {
                        addAssignmentImageResponse(Objects.requireNonNull(response.body()));
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<AddAssignmentResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                    Toast.makeText(getApplicationContext(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Constants.IntenetSettings(getApplicationContext());
        }

    }

    private void addAssignmentImageResponse(AddAssignmentResponse body) {
        if (body.getCode().equals("200")){
            updateImageList(body.getImgId(),body.getImgUrl());
            Toast.makeText(this, body.getDescription(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, body.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateImageList(String imgId, String imgUrl) {
        AddAssignmentModel list = new AddAssignmentModel(imgId, imgUrl);
        addAssignmentModels.add(list);
        addingAssignmentsAdapter.notifyDataSetChanged();
    }

    private void checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            int MULTIPLE_PERMISSIONS = 10;
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSIONS);
        }
    }

    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(R.string.app_name);
            progress.setMessage("Loading......");
        }
        progress.show();
        progress.setCancelable(false);
    }

    private void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

}