package com.credoapp.parent.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.addAssignment.AccademicYearRequest;
import com.credoapp.parent.model.addAssignment.AccademicyearModel;
import com.credoapp.parent.model.addAssignment.AccademicyearResponse;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.snackbar.Snackbar;
import com.credoapp.parent.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private View customView;
    private String acdemicName;
    private TextView accademic_year;
    private String adminid;
    private ProgressDialog progress;
    private int academicyear;
    private ArrayList<String> academicList, academicIdInList;


    public void setupActionBar(String feesText) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_title_bar);
        customView = getSupportActionBar().getCustomView();

//        ViewDataBinding viewDataBinding = DataBindingUtil.bind(customView);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0, 0);
        ((TextView) customView.findViewById(R.id.custom_title)).setText(feesText);
        ((TextView) customView.findViewById(R.id.custom_title)).setAllCaps(true);
        customView.findViewById(R.id.back_button).setOnClickListener(v -> onBackPressed());

        accademic_year = customView.findViewById(R.id.acedemicYearView);

        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        adminid = preferences.getString(Constants.ADMIN_ID, adminid);
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
        if(!preferences.contains(Constants.DEFAULTACADEMIC_ID)) {
            getAcademic();
        }
    }

    private void getAcademic() {
        if (adminid== null){
            return;
        }
        showLoadingDialog();
        AccademicYearRequest request = new AccademicYearRequest();

        if(adminid!=null) {
            request.setAdmin_id(Integer.parseInt(adminid));


        };//adminid
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
            Constants.IntenetSettings(ParentActivity.this);
        }
    }

    private void accademicResponse(AccademicyearResponse body) {
        dismissLoadingDialog();
        String response = body.getCode();
        String description = body.getDescription();
        academicyear = Integer.parseInt(body.getDefault_academic_year());
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        preferences.edit().putString(Constants.DEFAULTACADEMIC_ID, String.valueOf(academicyear)).apply();
        academicList = new ArrayList<>();
        academicIdInList = new ArrayList<>();

        if ("200".equals(response)) {
            dismissLoadingDialog();
            List<AccademicyearModel> accademicyearModels = body.getData();
            String academincYearReadable = "Select";
            for (int i = 0; i < accademicyearModels.size(); i++) {
                String academicYear = accademicyearModels.get(i).getAcademic_year();
                academicList.add(academicYear);
                String accademicId = accademicyearModels.get(i).getId();
                academicIdInList.add(accademicId);
                if (Integer.parseInt(accademicyearModels.get(i).getId()) == academicyear) {
                    academincYearReadable = academicYear;
                }
            }
            accademic_year.setText(academincYearReadable);
            // setSpinnerAccademic();
        } else {
            // setSpinnerAccademic();
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        //accademic_year.setText(acdemicName);
        Log.d("Mounika", "onResume: "+acdemicName);
        if (accademic_year != null) {
            accademic_year.setText(acdemicName);
            accademic_year.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            });
        }else {
            getAcademic();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
