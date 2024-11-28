package com.credoapp.parent.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.AddingAssignmentsAdapter;
import com.credoapp.parent.adapter.RepliedAssignmentsAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.addAssignment.AddAssignmentModel;
import com.credoapp.parent.model.addAssignment.GetAddedAssignmentImagesRequest;
import com.credoapp.parent.model.addAssignment.GetAddedAssignmentImagesResponse;
import com.credoapp.parent.model.addAssignment.RepliedAssignmentRequest;
import com.credoapp.parent.retrofit.ITutorSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRepliedAssignments extends AppCompatActivity {
    private static final String TAG = AddAssignmentActivity.class.getSimpleName();
    private final List<AddAssignmentModel> addAssignmentModels = new ArrayList<>();
    RecyclerView recyclerViewAddingAssignments;
    private MultipartBody.Part multiPartBody;
    public String assignmentId,studentId;
    TextView addButtonForAssignment;
    RepliedAssignmentsAdapter addingAssignmentsAdapter;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_replied_assignments);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        studentId = preferences.getString(Constants.USER_IDD, studentId);

        assignmentId = Objects.requireNonNull(getIntent().getExtras()).getString("assignmentId");
        String viewSubmit = getIntent().getExtras().getString("viewSubmit");
        findViewById(R.id.backLayoutAddAssignment).setOnClickListener(view -> onBackPressed());
        recyclerViewAddingAssignments = findViewById(R.id.recyclerViewAddingAssignments);
        //addButtonForAssignment = findViewById(R.id.addButtonForAssignment);
        TextView headerTextAddViewAssignment = findViewById(R.id.headerTextAddViewAssignment);
        assert viewSubmit != null;
        if (viewSubmit.equals("view")){
            headerTextAddViewAssignment.setText("REPLAY ASSIGNMENT IMAGES");
           // addButtonForAssignment.setVisibility(View.GONE);
        }else {
            headerTextAddViewAssignment.setText("REPLAY ASSIGNMENT IMAGES");
           // addButtonForAssignment.setVisibility(View.VISIBLE);
        }
        getImagesListSubmited();
        int numberOfColumns = 2;
        recyclerViewAddingAssignments.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        addingAssignmentsAdapter = new RepliedAssignmentsAdapter(ViewRepliedAssignments.this, addAssignmentModels);
        recyclerViewAddingAssignments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAddingAssignments.setAdapter(addingAssignmentsAdapter);

    }
    private void getImagesListSubmited() {
        showLoadingDialog();
        RepliedAssignmentRequest request = new RepliedAssignmentRequest();
        request.setAssignmentId(assignmentId);
        request.setStudentId(studentId);

        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().repliedAssignments(request).enqueue(new Callback<GetAddedAssignmentImagesResponse>() {
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