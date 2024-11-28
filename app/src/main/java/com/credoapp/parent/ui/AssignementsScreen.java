package com.credoapp.parent.ui;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.widget.Toast;

import com.credoapp.parent.R;
import com.google.gson.JsonObject;
import com.credoapp.parent.adapter.AssignmentsAdapter;
import com.credoapp.parent.databinding.ActivityAssignmentsBinding;
import com.credoapp.parent.model.AssignmentResponse;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.utils.Utils;

public class AssignementsScreen extends ParentActivity {
    private static final String TAG = AssignementsScreen.class.getSimpleName();
    //    StudentInfo studentInfo;
    private ActivityAssignmentsBinding binding;
    private String student_id,class_id,cameFrom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_assignments);
        binding = (ActivityAssignmentsBinding) viewDataBinding;
        setupActionBar("Assignments");
        cameFrom = getIntent().getStringExtra("cameFrom");
        student_id = getIntent().getStringExtra("student_id");
        class_id = getIntent().getStringExtra("classId");

        try {
            Utils.showProgressBar(this);
            StudentPresenter studentPresenter = new StudentPresenter(this);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("student_id", "" + student_id);
            jsonObject.addProperty("class_id", "" + class_id);
            studentPresenter.onRequestAssigments(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onSuccessAssignments(AssignmentResponse assignmentResponse) {
        Utils.hideProgressBar();
        if (assignmentResponse.getAssignments() != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignementsScreen.this,
                    LinearLayoutManager.VERTICAL, false);
            binding.studentAssigmentsList.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.studentAssigmentsList.getContext(),
                    linearLayoutManager.getOrientation());
            AssignmentsAdapter parentStudentsAdapter = new AssignmentsAdapter(this, assignmentResponse.getAssignments());
//            binding.studentAssigmentsList.addItemDecoration(dividerItemDecoration);
            binding.studentAssigmentsList.setAdapter(parentStudentsAdapter);
        } else {
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        if (cameFrom != null && cameFrom.equals("splash")) {
            Intent in = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(in);
            finish();

        } else {
            super.onBackPressed();
        }
    }
    }
