package com.credoapp.parent.ui;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.fragment.DashboardFragment;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.addAssignment.AccademicYearRequest;
import com.credoapp.parent.model.addAssignment.AccademicyearModel;
import com.credoapp.parent.model.addAssignment.AccademicyearResponse;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.snackbar.Snackbar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private FrameLayout contentFrame;
    private Toolbar toolbar;
    private StudentInfo student_info;
    private SharedPref sharedPref;
    private final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private String acdemicName;
    TextView accademic_year1;
    private ProgressDialog progress;
    private String adminid;
    private int academicyear;
    private ArrayList<String> academicList, academicIdInList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        contentFrame = findViewById(R.id.content_frame);
        toolbar = findViewById(R.id.toolbar);
        TextView page_title = findViewById(R.id.page_title);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        sharedPref = SharedPref.getmSharedPrefInstance(this);
        student_info = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
//        getSupportActionBar().setIcon(R.drawable.ic_back_arrow);
        loadDashBoard();
        findViewById(R.id.back_button).setOnClickListener(v -> {
//                onBackPressed();
            Intent in = new Intent(getApplicationContext(),ParentStudentsScreen.class);
            startActivity(in);
            finish();
        });
        accademic_year1 = findViewById(R.id.accademic_year1);

        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        adminid = preferences.getString(Constants.ADMIN_ID, adminid);
        Log.d("MS",adminid);



    }
    @Override
    protected void onResume() {
        super.onResume();
       // SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
        if(!sp.contains(Constants.DEFAULTACADEMIC_ID)) {
            getAcademic();
        } else {
            acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
            accademic_year1.setText(acdemicName);
        }
        //    getAcademic();

       // accademic_year1.setText(preferences.getString(Constants.DEFAULTACADEMIC_NAME, "Select"));

        //
    }


    private void getAcademic() {
        if (adminid== null){
            return;
        }
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
                Constants.IntenetSettings(MainActivity.this);
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
        Log.e("TAG", "accademicResponse: "+body );
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
                    preferences.edit().putString(Constants.DEFAULTACADEMIC_NAME, academincYearReadable).apply();
                    break;
                }
            }
            accademic_year1.setText(academincYearReadable);
            Log.d("academincYearReadable",academincYearReadable);
            // setSpinnerAccademic();
        } else {
            // setSpinnerAccademic();
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
        adminid = preferences.getString(Constants.ADMIN_ID, adminid);
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }



    private void loadDashBoard() {
        checkPermissions();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("student_info", Parcels.wrap(student_info));
        dashboardFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame, dashboardFragment).commitAllowingStateLoss();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.my_options_menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            sharedPref.saveParentId("");
            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, LoginScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.change_password) {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

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
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
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
