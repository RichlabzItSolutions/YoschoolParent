package com.credoapp.parent.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.credoapp.parent.adapter.DonationAdaptor;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.donateListModels.DonateListRequest;
import com.credoapp.parent.model.donateListModels.DonateListResponse;
import com.credoapp.parent.model.donateListModels.MyDonationsList;
import com.credoapp.parent.retrofit.ITutorSource;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.credoapp.parent.R;
public class DonateBooksList extends AppCompatActivity {

    private static final String TAG = DonateBooksList.class.getSimpleName();
    private String studentId, adminId;
    private ProgressDialog progress;
    private DonationAdaptor mAdapter;
    private final List<MyDonationsList> donations = new ArrayList<>();
//    private final String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_books_list);
//        Toolbar toolbar = findViewById(R.id.toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

//        checkPermissions();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        studentId = sp.getString(Constants.PARENT_ID, studentId);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);

//        getDonationList();
        LinearLayout backLayoutDonateList = findViewById(R.id.backLayoutDonateList);
        backLayoutDonateList.setOnClickListener(view -> {
//            Intent in = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(in);
//            finish();
            onBackPressed();
        });
        Button donate_books = findViewById(R.id.donate_books);
        donate_books.setOnClickListener(view -> {
            if (Constants.haveInternet(getApplicationContext())) {
                Intent in = new Intent(getApplicationContext(), DonateBooksActivity.class);
                startActivity(in);
            } else {
                Constants.InternetSettings(DonateBooksList.this);
            }
        });
        RecyclerView donateListRecyclerView = findViewById(R.id.donateListRecyclerView);
        mAdapter = new DonationAdaptor(DonateBooksList.this, donations);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        donateListRecyclerView.setLayoutManager(mLayoutManager);
        donateListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        donateListRecyclerView.setAdapter(mAdapter);


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

    @Override
    protected void onResume() {
        getDonationList();
        super.onResume();

    }

    public void getDonationList() {
        showLoadingDialog();
        DonateListRequest request = new DonateListRequest();
        request.setStudentId(studentId);
        request.setAdminId(adminId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().donateBooksList(request).enqueue(new Callback<DonateListResponse>() {
                @Override
                public void onResponse(@NonNull Call<DonateListResponse> call, @NonNull Response<DonateListResponse> response) {
                    Log.d(TAG, "onResponse donation list: " + response);

                    dismissLoadingDialog();


                    if (response.isSuccessful()) {
                        donationResponse(Objects.requireNonNull(response.body()));

                    } else {

                        switch (response.code()) {
                            case 404:
                                Toast.makeText(DonateBooksList.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(DonateBooksList.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(DonateBooksList.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<DonateListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            dismissLoadingDialog();
            Constants.InternetSettings(DonateBooksList.this);
        }
    }

    private void donationResponse(DonateListResponse body) {
        String response = body.getResponseCode();
        String description = body.getDescription();
        if ("200".equals(response)) {
            donations.clear();
            MyDonationsList myDonate = new MyDonationsList();
            myDonate.setTitle("My Donations");
            MyDonationsList otherDonate = new MyDonationsList();
            otherDonate.setTitle("Other Donations");


            int lenMyDonations = body.getMyDonationsLists().size();
            int lenOtherDonations = body.getOthersDonationsLists().size();

            Log.d("lengths", lenMyDonations + " , " + lenOtherDonations);

            if (lenMyDonations == 0) {
                donations.add(myDonate);
                MyDonationsList emptyItem = new MyDonationsList();
                emptyItem.setEmptyTitle("You are not posted any donations yet");
                emptyItem.setTitle("empty");
                donations.add(emptyItem);
            } else {
                donations.add(myDonate);
                for (MyDonationsList don : body.getMyDonationsLists()) {
                    don.setTitle("my");
                    donations.add(don);
                }
            }
            if (lenOtherDonations == 0) {
                donations.add(otherDonate);
                MyDonationsList emptyItem = new MyDonationsList();
                emptyItem.setEmptyTitle("No donations found");
                emptyItem.setTitle("empty");
                donations.add(emptyItem);
            } else {
                donations.add(otherDonate);
                for (MyDonationsList don : body.getOthersDonationsLists()) {
                    don.setTitle("other");
                    donations.add(don);
                }
            }

            mAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(findViewById(android.R.id.content), R.string.unexpectedError, Snackbar.LENGTH_SHORT).show();
        }
    }

//    private void checkPermissions() {
//        int result;
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p : permissions) {
//            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(p);
//            }
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            int MULTIPLE_PERMISSIONS = 10;
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
//        }
//    }

    @Override
    public void onBackPressed() {
//        Intent in = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(in);
//        finish();
        super.onBackPressed();
    }
}
