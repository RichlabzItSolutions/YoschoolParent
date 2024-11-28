package com.credoapp.parent.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.GlobalResponse;
import com.credoapp.parent.model.ParentDetails;
import com.credoapp.parent.model.updateStudentModels.UpdateStudentRequest;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.textfield.TextInputEditText;

import android.os.Handler;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.credoapp.parent.model.StudentDetails;
import com.google.gson.Gson;


import org.parceler.Parcels;

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
import com.credoapp.parent.R;
import retrofit2.Response;

public class EditInfoStudentScreen extends ParentActivity {
    private static final String TAG = EditInfoStudentScreen.class.getSimpleName();
    private StudentDetails student_info;
    private ImageView profile_icon;
    private Button submitButton;
    private MultipartBody.Part multiPartBody;
    private ProgressDialog progress;

    TextInputEditText studentname, rollnumber, class_selection, gender_text, mothers_name_text,
            nationality_text, date_of_joining_text, date_of_birth_text, address_text, religion_text;

    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<CropImageContractOptions> crop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar("Student Info");
        setContentView(R.layout.edit_info_student_layout);
        student_info = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        ParentDetails parent_info = Parcels.unwrap(getIntent().getParcelableExtra("parent_info"));
        Log.e("=======>", "onCreate: " + student_info.getId());
        profile_icon = findViewById(R.id.profile_icon);
        studentname = findViewById(R.id.studentname);
        rollnumber = findViewById(R.id.rollnumber);
        RelativeLayout profileIconLayout = findViewById(R.id.top_profile_view);
        class_selection = findViewById(R.id.class_selection);
        gender_text = findViewById(R.id.gender_text);
        mothers_name_text = findViewById(R.id.mothers_name_text);
        nationality_text = findViewById(R.id.nationality_text);
        date_of_joining_text = findViewById(R.id.date_of_joining_text);
        date_of_birth_text = findViewById(R.id.date_of_birth_text);
        address_text = findViewById(R.id.address_text);
        submitButton = findViewById(R.id.submit_button);
        religion_text = findViewById(R.id.religion_text);

        Glide.with(this).load(student_info.getPhoto()).placeholder(R.drawable.yo_two).into(profile_icon);

        registerReciver();
        studentname.setText(student_info.getName());
        rollnumber.setText(student_info.getRoll_number());
        gender_text.setText(student_info.getGender());
        mothers_name_text.setText(student_info.getMother_name());
        nationality_text.setText(student_info.getNationality());
        date_of_joining_text.setText(student_info.getDate_of_join());
        date_of_birth_text.setText(student_info.getBirthday());
        address_text.setText(student_info.getStudent_address());
        class_selection.setText(student_info.getClass_name());
        religion_text.setText(student_info.getReligion());

        profileIconLayout.setOnClickListener(v -> {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
            if (result == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(EditInfoStudentScreen.this, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(this::checkPermissions, 2000);
                crop.launch(new CropImageContractOptions(null, new CropImageOptions()));
            }
            else
            {
                checkPermissions();
            }
        });


        studentname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Objects.requireNonNull(studentname.getText()).toString().matches("^ ")) {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                    studentname.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        address_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Objects.requireNonNull(address_text.getText()).toString().matches("^ ")) {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                    address_text.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nationality_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Objects.requireNonNull(nationality_text.getText()).toString().matches("^ ")) {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                    nationality_text.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mothers_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Objects.requireNonNull(mothers_name_text.getText()).toString().matches("^ ")) {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                    mothers_name_text.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        submitButton.setOnClickListener(v -> {

            if (Objects.requireNonNull(studentname.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Student Name", Toast.LENGTH_SHORT).show();
                return;
            }
//                if (rollnumber.getText().toString().equals("")) {
//                    Toast.makeText(EditInfoStudentScreen.this, "Enter Roll Number", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            if (Objects.requireNonNull(class_selection.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Class And Section", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(gender_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Gender", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(mothers_name_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Mother's Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(religion_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Religion ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(nationality_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Nationality", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(date_of_joining_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Date of Joining", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(date_of_birth_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Date Of Birth", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.requireNonNull(address_text.getText()).toString().equals("")) {
                Toast.makeText(EditInfoStudentScreen.this, "Enter Address", Toast.LENGTH_SHORT).show();
                return;
            }


            updateStudentInfo();
        });

    }
    private void registerReciver() {
        crop = registerForActivityResult(new CropImageContract(), result -> {
            Uri selectedFileUri = result.getUriContent();
            if (selectedFileUri != null) {
                File f = uriToFile(selectedFileUri, this);
                if (f != null) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                    profile_icon.setImageBitmap(myBitmap);
                    //imageViewAssignmentOfPrevious.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
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
    private void updateStudentInfo() {

        showLoadingDialog();


        UpdateStudentRequest request = new UpdateStudentRequest();
        request.setAddress(Objects.requireNonNull(address_text.getText()).toString());
        request.setMotherName(Objects.requireNonNull(mothers_name_text.getText()).toString());
        request.setNationality(Objects.requireNonNull(nationality_text.getText()).toString());
        request.setStudentName(Objects.requireNonNull(studentname.getText()).toString());
        request.setStudentId(student_info.getId());


        String requestData = new Gson().toJson(request);
        Log.d(TAG, "submitStudentForm: " + requestData);
        //RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, requestData);


        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().updateStudent(request).enqueue(new Callback<GlobalResponse>() {
                @Override
                public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                    Log.d(TAG, "onResponse: " + response);

                    dismissLoadingDialog();

                    if (response.isSuccessful()) {
                        updateStudentResponse(Objects.requireNonNull(response.body()));
                    } else {
                        Toast.makeText(EditInfoStudentScreen.this, "not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.InternetSettings(EditInfoStudentScreen.this);
        }
    }

    private void updateStudentResponse(GlobalResponse body) {
        if (body.getResponseCode().equals("200")) {
            Intent in = new Intent(getApplicationContext(), ParentStudentsScreen.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
        }
        Toast.makeText(this, body.getDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                Log.d(TAG, "onActivityResult: "+resultUri);
//                uploadFile(resultUri);
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            Log.d(TAG, "onActivityResult: uriPaths        " + returnValue);
//            Uri uri = Uri.fromFile(new File(returnValue.get(0)));
//            Uri fileUrie = data.getData();
//            Log.d(TAG, "onActivityResult: fileUrie   " + fileUrie);
            Uri myUri = Uri.parse(returnValue.get(returnValue.size() - 1));


            File imgFile = new File(returnValue.get(returnValue.size() - 1));
            Log.d(TAG, "onActivityResult: " + imgFile);

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profile_icon.setImageBitmap(myBitmap);

            }
            uploadFile(myUri);
        }
    }
*/

    private void uploadFile(Uri myUri) {


        File file = new File(myUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);

        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getApplicationContext().getContentResolver().getType(fileUri)),
//                        file
//                );


        // MultipartBody.Part is used to send also the actual file name
        multiPartBody = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);





    }


    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(R.string.loading_title);
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


}
