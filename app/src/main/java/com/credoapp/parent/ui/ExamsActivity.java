package com.credoapp.parent.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.adapter.ExamSubjectAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.ExamDateResponse;
import com.credoapp.parent.model.examsModels.ExamDateRequest;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.utils.Utils;
import com.credoapp.parent.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private RecyclerView examDatesTitle;
    private String adminId,studentId,class_id;
    private static final String TAG = ExamsActivity.class.getSimpleName();
    TextView accademic_year1;
    private String acdemicName;
    private String accdomicId;
    /*String[] academicyear = { "2021-2022", "2022-2023", "2023-2024", "2024-2025", "2025-2026"};
    Spinner accademic_year;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        class_id = getIntent().getStringExtra("classId");
        studentId = getIntent().getStringExtra("student_id");
        accdomicId=sp.getString(Constants.DEFAULTACADEMIC_ID,accdomicId);
        accademic_year1 = findViewById(R.id.accademic_year1);
        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });
        examDatesTitle = findViewById(R.id.examDatesTitle);

        LinearLayout backLayoutExams = findViewById(R.id.backLayoutExams);
        backLayoutExams.setOnClickListener(v -> {
            onBackPressed();
        });

        getExamsResults();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        accademic_year1.setText(acdemicName);
        accdomicId=sp.getString(Constants.DEFAULTACADEMIC_ID,accdomicId);
        Utils.hideProgressBar();
        getExamsResults();
    }
    private void getExamsResults() {
        Utils.showProgressBar(ExamsActivity.this);
        ExamDateRequest request = new ExamDateRequest();
        request.setStudentId(studentId);
        request.setClassId(class_id);
        request.setAccademicYear(accdomicId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getExamsResults(request).enqueue(new Callback<ExamDateResponse>() {
                @Override
                public void onResponse(@NonNull Call<ExamDateResponse> call, @NonNull Response<ExamDateResponse> response) {
                    Log.d(TAG, "onResponse1: " + response);
                    Utils.hideProgressBar();
                    if (response.body() != null) {
                        getExamsList(response.body());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ExamDateResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Utils.hideProgressBar();
                }
            });
        } else {
            Utils.hideProgressBar();
            Constants.IntenetSettings(ExamsActivity.this);
        }
    }
    private void getExamsList(ExamDateResponse body) {
        String response = String.valueOf(body.getCode());
        String description = body.getDescription();
        if (response.equals("200")){
            examDatesTitle.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExamsActivity.this, LinearLayoutManager.VERTICAL, false);
            examDatesTitle.setLayoutManager(linearLayoutManager);
            ExamSubjectAdapter mAdapter = new ExamSubjectAdapter(ExamsActivity.this,body.getExamTimeTableResponse().getExamList());
            examDatesTitle.setAdapter(mAdapter);
        }else {
            examDatesTitle.setVisibility(View.GONE);
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}
