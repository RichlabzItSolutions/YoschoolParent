package com.credoapp.parent.events;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.ui.SelectAcademic;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.credoapp.parent.R;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = EventsActivity.class.getSimpleName();
    ProgressDialog progress;
    RecyclerView recyclerView_events_list;
    LinearLayout back_layout_events;
    EventsAdaptor mAdapter;
    String adminId;
    private final List<EventsResultsList> eventsResultsListList = new ArrayList<>();
    private String acdemicName;
    TextView accademic_year1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        adminId = SharedPref.getmSharedPrefInstance(this).getAdminID();
        recyclerView_events_list = findViewById(R.id.recyclerView_events_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        back_layout_events = findViewById(R.id.back_layout_events);
        back_layout_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getEventsList();

        accademic_year1 = findViewById(R.id.accademic_year1);
        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });
        mAdapter = new EventsAdaptor(getApplicationContext(), eventsResultsListList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView_events_list.setLayoutManager(mLayoutManager);
        recyclerView_events_list.setItemAnimator(new DefaultItemAnimator());
        recyclerView_events_list.setAdapter(mAdapter);


    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        accademic_year1.setText(acdemicName);

    }

    private void getEventsList() {
//        showLoadingDialog();
//        EventsRequest request = new EventsRequest();
//        request.setAdminId(adminId);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("admin_id", adminId);
//        ParentPresenter parentPresenter = new ParentPresenter(this);
//        parentPresenter.onRequestEvents(jsonObject);


        showLoadingDialog();
        EventsRequest request = new EventsRequest();
        request.setAdminId(adminId);

        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getEventss(request).enqueue(new Callback<EventsResponse>() {
                @Override
                public void onResponse(@NonNull Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {
                    Log.d(TAG, "onResponse : " + response);
                    eventsLists(Objects.requireNonNull(response.body()));
                    dismissLoadingDialog();
                }
                @Override
                public void onFailure(@NonNull Call<EventsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        }else {
            Constants.InternetSettings(EventsActivity.this);
        }
    }

    private void eventsLists(EventsResponse body) {

        Log.e(TAG, "eventsList: 22222" );
        dismissLoadingDialog();
        String response = body.getResponseCode();
        String description = body.getDescription();

        if ("200".equals(response)) {
            eventsResultsListList.clear();
            eventsResultsListList.addAll(body.getResults());
            mAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void eventsList(EventsResponse body) {

        Log.e(TAG, "eventsList: " );
        dismissLoadingDialog();
        String response = body.getResponseCode();
        String description = body.getDescription();

        if ("200".equals(response)) {
            eventsResultsListList.clear();
            eventsResultsListList.addAll(body.getResults());
            mAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
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

    public void onSuccessEvents(EventsResponse eventsResponse) {
        dismissLoadingDialog();
        eventsList(eventsResponse);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
