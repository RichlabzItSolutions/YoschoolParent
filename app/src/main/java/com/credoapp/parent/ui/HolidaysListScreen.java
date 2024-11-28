package com.credoapp.parent.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.credoapp.parent.adapter.HolidaysListAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.holidaysModels.HolidaysListRequest;
import com.credoapp.parent.model.holidaysModels.HolidaysListResponse;
import com.credoapp.parent.model.holidaysModels.HolidaysListResults;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.snackbar.Snackbar;
import com.credoapp.parent.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolidaysListScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = HolidaysListScreen.class.getSimpleName();
    private ArrayList<HolidaysListResults> holidaysListResults = new ArrayList<>();
    private HolidaysListAdapter mAdapter;
    private RecyclerView recyclerViewHolidaysLists;
    private ProgressDialog progress;
    private String adminId;
    private String acdemicName;
    TextView accademic_year1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays_list_screen);


        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);

        LinearLayout backLayoutHolidaysList  = findViewById(R.id.backLayoutHolidaysList);
        backLayoutHolidaysList.setOnClickListener(v -> {
            onBackPressed();
        });
        recyclerViewHolidaysLists = findViewById(R.id.recyclerViewHolidaysLists);

        mAdapter = new HolidaysListAdapter(HolidaysListScreen.this, holidaysListResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewHolidaysLists.setLayoutManager(mLayoutManager);
        recyclerViewHolidaysLists.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHolidaysLists.setAdapter(mAdapter);

        accademic_year1 = findViewById(R.id.accademic_year1);
        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });
        getHolidaysList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        accademic_year1.setText(acdemicName);
        getHolidaysList();

    }

    private void getHolidaysList() {
        showLoadingDialog();
        HolidaysListRequest request = new HolidaysListRequest();
        request.setAdminId(adminId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getHolidaysList(request).enqueue(new Callback<HolidaysListResponse>() {
                @Override
                public void onResponse(@NonNull Call<HolidaysListResponse> call, @NonNull Response<HolidaysListResponse> response) {
                    Log.d(TAG, "onResponse1: " + response);
                    dismissLoadingDialog();
                    holidaysResponse(Objects.requireNonNull(response.body()));
                }

                @Override
                public void onFailure(@NonNull Call<HolidaysListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.IntenetSettings(HolidaysListScreen.this);
        }
    }

    private void holidaysResponse(HolidaysListResponse body) {
        dismissLoadingDialog();
        String response = body.getCode();
        String description = body.getDescription();
        switch (response) {
            case "200":
                recyclerViewHolidaysLists.setVisibility(View.VISIBLE);
                holidaysListResults.clear();
                holidaysListResults.addAll(body.getResults());
                mAdapter.notifyDataSetChanged();
                break;
            case "204":
            default:
                Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(this, description , Toast.LENGTH_SHORT).show();
                recyclerViewHolidaysLists.setVisibility(View.GONE);
                break;
        }

    }



    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(getString(R.string.loading_title));
            progress.setMessage("loading.......");
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
