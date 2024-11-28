package com.credoapp.parent.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.adapter.MonthlySyllabusAdaptor;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.monthlySllabusModels.GetMonthlySyllabusListRequest;
import com.credoapp.parent.model.monthlySllabusModels.GetMonthlySyllabusListResponse;
import com.credoapp.parent.model.monthlySllabusModels.GetMonthlySyllabusListResults;
import com.credoapp.parent.model.pdfModels.PdfRequest;
import com.credoapp.parent.model.pdfModels.PdfResponse;
import com.credoapp.parent.model.pdfModels.PdfResults;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.retrofit.ITutorSource;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.credoapp.parent.R;
public class PdfActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = PdfActivity.class.getSimpleName();
    private ProgressDialog progress;
//    private PdfAdaptor mAdapter;
    private MonthlySyllabusAdaptor mAdapter;
    private SharedPref sharedPref;
    private String adminId;
    private ArrayList<GetMonthlySyllabusListResults> getMonthlySyllabusListResults = new ArrayList<>();
    private int monthSpinnerPosition;
    private String[] monthsList  = {"Select Month","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private StudentInfo studentInfo;
    private ArrayList<PdfResults> pdfResults = new ArrayList<>();
    private String cameFrom,student_id,class_id_push,class_id;
    private RecyclerView recyclerViewPdfLists;
    private String acdemicName;
    TextView accademic_year1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Toolbar toolbar = findViewById(R.id.toolbarPdf);
        setSupportActionBar(toolbar);

        cameFrom = getIntent().getStringExtra("cameFrom");
        student_id = getIntent().getStringExtra("student_id");
        class_id_push = getIntent().getStringExtra("classId");


        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        class_id = sp.getString(Constants.CLASS_ID, class_id);


        Log.e(TAG, "onCreate: "+class_id_push );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        LinearLayout backLayoutMonthlySyllabus = findViewById(R.id.backLayoutMonthlySyllabus);
        backLayoutMonthlySyllabus.setOnClickListener(v -> {
            if (cameFrom != null && cameFrom.equals("splash")) {
                Intent in = new Intent(getApplicationContext(), SplashScreen.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            } else {
                onBackPressed();
            }
        });


        sharedPref = SharedPref.getmSharedPrefInstance(this);
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        //Log.d(TAG, "onCreate: "+studentInfo.getStudent_id()+"    ,     "+sharedPref.getParentId());
        recyclerViewPdfLists = findViewById(R.id.recyclerViewPdfLists);
        Spinner monthSpinnerViewFrag = findViewById(R.id.monthSpinnerViewFrag);


        ArrayAdapter monthArray = new ArrayAdapter<>(Objects.requireNonNull(getApplicationContext()), R.layout.spinner_item, monthsList);
        monthArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinnerViewFrag.setAdapter(monthArray);
        monthSpinnerViewFrag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSpinnerPosition = i;
                getMonthlySyllabusList(monthSpinnerPosition);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        monthSpinnerViewFrag.setSelection(month+1);


        mAdapter = new MonthlySyllabusAdaptor(getApplicationContext(), getMonthlySyllabusListResults);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewPdfLists.setLayoutManager(mLayoutManager);
        recyclerViewPdfLists.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPdfLists.setAdapter(mAdapter);

        accademic_year1 = findViewById(R.id.accademic_year1);
        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });


//        mAdapter = new PdfAdaptor(getApplicationContext(), pdfResults);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerViewPdfLists.setLayoutManager(mLayoutManager);
//        recyclerViewPdfLists.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewPdfLists.setAdapter(mAdapter);
//        getPdfsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        accademic_year1.setText(acdemicName);

    }
    private void getMonthlySyllabusList(int monthSpinnerPosition) {


        if (monthSpinnerPosition==0){
            return;
        }
        showLoadingDialog();
        GetMonthlySyllabusListRequest request = new GetMonthlySyllabusListRequest();
        if (cameFrom != null && cameFrom.equals("splash")) {
            //request.setStudentId(student_id);
            request.setClassId(class_id_push);
        }else {
            request.setClassId(class_id);
            //request.setStudentId(studentInfo.getStudent_id());
        }

        request.setAdminId(adminId);
        request.setMonth(String.valueOf(monthSpinnerPosition));
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().syllabusList(request).enqueue(new Callback<GetMonthlySyllabusListResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetMonthlySyllabusListResponse> call, @NonNull Response<GetMonthlySyllabusListResponse> response) {
                    Log.d(TAG, "onResponse1: " + response);
                    dismissLoadingDialog();
                    getSyllabusList(Objects.requireNonNull(response.body()));
                }

                @Override
                public void onFailure(@NonNull Call<GetMonthlySyllabusListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.IntenetSettings(PdfActivity.this);
        }

    }

    private void getSyllabusList(GetMonthlySyllabusListResponse body) {
        String response = body.getCode();
        String description = body.getDescription();

        if ("200".equals(response)) {
            recyclerViewPdfLists.setVisibility(View.VISIBLE);
            getMonthlySyllabusListResults.clear();
            getMonthlySyllabusListResults.addAll(body.getResults());
            mAdapter.notifyDataSetChanged();
        } else {
            recyclerViewPdfLists.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            //Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }

    }

    private void getPdfsList() {
        showLoadingDialog();
        PdfRequest request = new PdfRequest();
        request.setUserId(sharedPref.getParentId());
        request.setStudentId(studentInfo.getStudent_id());
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().pdfsList(request).enqueue(new Callback<PdfResponse>() {
                @Override
                public void onResponse(@NonNull Call<PdfResponse> call, @NonNull Response<PdfResponse> response) {
                    Log.d(TAG, "onResponse1: " + response);
                    if (response.body() != null) {
                        getPdfList(response.body());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<PdfResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.IntenetSettings(PdfActivity.this);
        }
    }
    private void getPdfList(PdfResponse body) {
        dismissLoadingDialog();
        String response = body.getCode();
        String description = body.getDescription();
        if ("200".equals(response)) {
            recyclerViewPdfLists.setVisibility(View.VISIBLE);
            pdfResults.clear();
            pdfResults.addAll(body.getResults());
            mAdapter.notifyDataSetChanged();
        } else {
            recyclerViewPdfLists.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            //Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }
    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(getString(R.string.loading_title));
            progress.setMessage("loading.....");
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
    public void onBackPressed() {
        if (cameFrom != null && cameFrom.equals("splash")) {
            Intent in = new Intent(getApplicationContext(), SplashScreen.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
