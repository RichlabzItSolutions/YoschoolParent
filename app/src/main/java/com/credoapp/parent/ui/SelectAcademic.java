package com.credoapp.parent.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.addAssignment.AccademicYearRequest;
import com.credoapp.parent.model.addAssignment.AccademicyearModel;
import com.credoapp.parent.model.addAssignment.AccademicyearResponse;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAcademic extends AppCompatActivity {

    Spinner accademic_year;
    Button button_saveaccademic;
    private SharedPreferences sp;
    String adminid;
    private ProgressDialog progress;
    int academicyear;
    private ArrayList<String> academicList, academicIdInList;
    private int accademicPosition;
    private String accademicIdInListString;
    private LinearLayout backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_academic);
        button_saveaccademic = findViewById(R.id.button_saveaccademic);
        accademic_year = findViewById(R.id.accademic_year);
        backIcon = findViewById(R.id.back_layout_academic);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminid = sp.getString(Constants.ADMIN_ID, adminid);
        getAcademic();
    }
    private void getAcademic() {
        showLoadingDialog();
        AccademicYearRequest request = new AccademicYearRequest();
        request.setAdmin_id(Integer.parseInt(adminid));//adminid
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getAcademicyears(request).enqueue(new Callback<AccademicyearResponse>() {
                @Override
                public void onResponse(@NonNull Call<AccademicyearResponse> call, @NonNull Response<AccademicyearResponse> response) {
                    accademicResponse(Objects.requireNonNull(response.body()));


                }

                @Override
                public void onFailure(@NonNull Call<AccademicyearResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.IntenetSettings(SelectAcademic.this);
        }
    }

    private void accademicResponse(AccademicyearResponse body) {
        dismissLoadingDialog();
        String response = body.getCode();
        String description = body.getDescription();
        academicyear = Integer.parseInt(body.getDefault_academic_year());
        //  accademic_year.setText(body.getDefault_academic_year());
      /* SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        preferences.edit().putString(Constants.DEFAULTACADEMIC_ID, String.valueOf(academicyear)).apply();*/
        academicList = new ArrayList<>();
        academicIdInList = new ArrayList<>();

        if ("200".equals(response)) {
            dismissLoadingDialog();
            List<AccademicyearModel> accademicyearModels = body.getData();
            for (int i = 0; i < accademicyearModels.size(); i++) {
                String academicYear = accademicyearModels.get(i).getAcademic_year();
                academicList.add(academicYear);
                String accademicId = accademicyearModels.get(i).getId();
                academicIdInList.add(accademicId);
            }
            setSpinnerAccademic();

        } else {
            setSpinnerAccademic();
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }


    private void setSpinnerAccademic() {
        academicList.add(0, "-Academic Year-");
        academicIdInList.add(0, "0");
        ArrayAdapter aa = new ArrayAdapter<>(this, R.layout.spinner_item, academicList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        accademic_year.setAdapter(aa);
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        int id = Integer.parseInt(preferences.getString(Constants.DEFAULTACADEMIC_ID, "0"));
        int selectedPosition = 0;
        for (int i = 0; i < academicIdInList.size(); i++) {
            if (Integer.parseInt(academicIdInList.get(i)) == id) {
                selectedPosition = i;
            }
        }
        accademic_year.setSelection(selectedPosition);
        accademic_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accademicPosition = i;
                if (accademicPosition == 0) {
                    //recyclerView.setVisibility(View.GONE);
                } else {
                    accademicIdInListString = academicIdInList.get(accademicPosition);
                    button_saveaccademic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            preferences.edit().putString(Constants.DEFAULTACADEMIC_ID, accademicIdInListString).apply();
                            preferences.edit().putString(Constants.DEFAULTACADEMIC_NAME, academicList.get(accademicPosition)).apply();
                            finish();
                        }
                    });
                    //accademic_year.setSelection(academicyear);


                    //getStudentDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(getString(R.string.loading_title));
            progress.setMessage(getString(R.string.loading_message));
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