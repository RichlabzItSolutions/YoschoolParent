package com.credoapp.parent.ui;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import com.credoapp.parent.adapter.VaccineAdaptor;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.vaccineModels.VaccineModelResponse;
import com.credoapp.parent.model.vaccineModels.VaccineModelResults;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.credoapp.parent.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccineScreen extends AppCompatActivity {
    VaccineAdaptor mAdapter;
    RecyclerView recyclerViewVaccine;
    private static final String TAG = VaccineScreen.class.getSimpleName();
    private final List<VaccineModelResults> vaccineModelResults = new ArrayList<>();
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

//        Objects.requireNonNull(getSupportActionBar()).setTitle("VACCINATION");
//
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        LinearLayout backLayoutVaccineList = findViewById(R.id.backLayoutVaccineList);
        backLayoutVaccineList.setOnClickListener(v -> onBackPressed());

        recyclerViewVaccine =findViewById(R.id.recyclerViewVaccine);

        mAdapter = new VaccineAdaptor(vaccineModelResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerViewVaccine.setLayoutManager(mLayoutManager);
        recyclerViewVaccine.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVaccine.setAdapter(mAdapter);
        getVaccines();
    }


    private void getVaccines() {
        showLoadingDialog();
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getVaccinesList().enqueue(new Callback<VaccineModelResponse>() {
                @Override
                public void onResponse(@NonNull Call<VaccineModelResponse> call, @NonNull Response<VaccineModelResponse> response) {
                    Log.d(TAG, "onResponse notification: " + response);
                    dismissLoadingDialog();
                    if (response.isSuccessful()){
                        getVaccinesResponse(Objects.requireNonNull(response.body()));
                    }else {
                        Toast.makeText(VaccineScreen.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<VaccineModelResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        }
    }

    private void getVaccinesResponse(VaccineModelResponse body) {
        if ("200".equals(body.getResponseCode())) {
            vaccineModelResults.clear();
            vaccineModelResults.addAll(body.getResults());
            mAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(findViewById(android.R.id.content), body.getDescription(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(getString(R.string.loading_title));
            progress.setMessage("Loading........");
        }
        progress.show();
        progress.setCancelable(false);
    }

    private void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

}
