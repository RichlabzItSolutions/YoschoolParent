package com.credoapp.parent.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.credoapp.parent.adapter.OnlineClassesAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassesRequest;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassesResponse;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassesResults;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.credoapp.parent.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineClassesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = OnlineClassesActivity.class.getSimpleName();
    private OnlineClassesAdapter mAdapter;
    private final List<OnlineClassesResults> onlineClassesResults = new ArrayList<>();
    private ProgressDialog progress;
    private String adminId,classId,studentid;
    RecyclerView recyclerViewNotification;
    StudentInfo studentInfo;
    private StudentPresenter studentPresenter;
    private String acdemicName;
    TextView accademic_year1;
    private String outputDateStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_classes);
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        classId = sp.getString(Constants.CLASS_ID, classId);
        studentid = sp.getString(Constants.USER_IDD, studentid);
        accademic_year1 = findViewById(R.id.accademic_year1);
        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });
        LinearLayout backLayoutNotifications = findViewById(R.id.backLayoutOnlineClasses);
        backLayoutNotifications.setOnClickListener(view -> finish());




        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);
        /** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarViewOnlineClasses)
                .range(startDate, endDate)
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSelected(Calendar date, int position) {
                String dateStr = String.valueOf(date);
                DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                Date dateS = null;
                try {
                    dateS = inputFormat.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                assert dateS != null;
                outputDateStr = outputFormat.format(dateS);
                //dateToday = outputDateStr;
                getClassesList(outputDateStr);
            }




        });


        recyclerViewNotification = findViewById(R.id.onlineClassesRecyclerView);
        mAdapter = new OnlineClassesAdapter(OnlineClassesActivity.this,onlineClassesResults,studentid);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerViewNotification.setLayoutManager(mLayoutManager);
        recyclerViewNotification.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNotification.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        accademic_year1.setText(acdemicName);
        getClassesList(outputDateStr);


    }
    private void getClassesList(String outputDateStr) {
        showLoadingDialog();
        OnlineClassesRequest request = new OnlineClassesRequest();
        request.setAdminId(adminId);
        request.setClassesDate(outputDateStr);
        request.setClassId(classId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getOnLineClassesData(request).enqueue(new Callback<OnlineClassesResponse>() {
                @Override
                public void onResponse(@NonNull Call<OnlineClassesResponse> call, @NonNull Response<OnlineClassesResponse> response) {
                    Log.d(TAG, "onResponse notification: " + response);
                    dismissLoadingDialog();
                    getOnLineClassesDataResponse(Objects.requireNonNull(response.body()));
                }
                @Override
                public void onFailure(@NonNull Call<OnlineClassesResponse> call, @NonNull Throwable t) {
                    recyclerViewNotification.setVisibility(View.GONE);
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        }
    }

    private void getOnLineClassesDataResponse(OnlineClassesResponse body) {
        String response = body.getResponseCode();
        if ("200".equals(response)) {
            recyclerViewNotification.setVisibility(View.VISIBLE);
            onlineClassesResults.clear();
            onlineClassesResults.addAll(body.getOnlineClassesResults());
            mAdapter.notifyDataSetChanged();
        } else {
            recyclerViewNotification.setVisibility(View.GONE);
            Toast.makeText(this, body.getDescription(), Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(android.R.id.content), body.getDescription(), Snackbar.LENGTH_SHORT).show();
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}