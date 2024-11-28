package com.credoapp.parent.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.TimeTableAdapterNew;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.timeTableModels.TimeTableRequest;
import com.credoapp.parent.model.timeTableModels.TimeTableResponse;
import com.credoapp.parent.model.timeTableModels.TimeTableResults;
import com.credoapp.parent.retrofit.ITutorSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeTable extends AppCompatActivity {

    private static final String TAG = TimeTable.class.getSimpleName();
    private RecyclerView timeTableRecyclerView;
    private String studentId,adminId,class_id;
    private TimeTableAdapterNew mAdapter;
    private ProgressDialog progress;
    private final List<TimeTableResults> timeTableResults = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        class_id = getIntent().getStringExtra("classId");
        studentId = getIntent().getStringExtra("student_id");
//        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
//        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("parent_id", sharedPref.getParentId());
//        jsonObject.addProperty("student_id", "" + studentInfo.getStudent_id());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        LinearLayout backLayoutTimeTable = findViewById(R.id.backLayoutTimeTable);
        timeTableRecyclerView = findViewById(R.id.timeTableRecyclerView);

        backLayoutTimeTable.setOnClickListener(v -> {
            onBackPressed();
        });


        mAdapter = new TimeTableAdapterNew(getApplicationContext(), timeTableResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        timeTableRecyclerView.setLayoutManager(mLayoutManager);
        timeTableRecyclerView.setItemAnimator(new DefaultItemAnimator());
        timeTableRecyclerView.setAdapter(mAdapter);



        getTimeTableList();

    }

    private void getTimeTableList() {
        showLoadingDialog();
        TimeTableRequest request = new TimeTableRequest();
        request.setStudentId(studentId);
        request.setAdminId(adminId);
        request.setClassId(class_id);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().tableList(request).enqueue(new Callback<TimeTableResponse>() {
                @Override
                public void onResponse(@NonNull Call<TimeTableResponse> call, @NonNull Response<TimeTableResponse> response) {
                    Log.d(TAG, "onResponse donation list: " + response);

                    dismissLoadingDialog();



                    if (response.isSuccessful()) {
                        getTimeList(Objects.requireNonNull(response.body()));

                    } else {

                        switch (response.code()) {
                            case 404:
                                Toast.makeText(TimeTable.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(TimeTable.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(TimeTable.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }


                }

                @Override
                public void onFailure(@NonNull Call<TimeTableResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            dismissLoadingDialog();
            Constants.InternetSettings(TimeTable.this);
        }



    }

    private void getTimeList(TimeTableResponse body) {
        String response = String.valueOf(body.getCode());
        String description = body.getDescription();

        if ("200".equals(response)) {
            timeTableRecyclerView.setVisibility(View.VISIBLE);
            timeTableResults.clear();
            timeTableResults.addAll(body.getResults());
            mAdapter.notifyDataSetChanged();
        } else {
            timeTableRecyclerView.setVisibility(View.GONE);
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
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

}
