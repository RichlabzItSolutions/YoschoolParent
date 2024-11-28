package com.credoapp.parent.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.credoapp.parent.model.addAssignment.AccademicYearRequest;
import com.credoapp.parent.model.addAssignment.AccademicyearModel;
import com.credoapp.parent.model.addAssignment.AccademicyearResponse;
import com.github.javiersantos.appupdater.AppUpdater;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.appVersionModels.AppVersionResponse;
import com.credoapp.parent.model.paymentModels.PaymentRequestIds;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.credoapp.parent.adapter.ParentStudentsAdapter;

import com.credoapp.parent.model.StudentsResponse;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ActivityParentStudentsBinding;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentStudentsScreen extends ParentActivity
        implements PaymentResultListener {
    private static final String TAG = ParentStudentsScreen.class.getSimpleName();
    private ActivityParentStudentsBinding binding;
    private SharedPref sharedPref;
    private ParentPresenter parentPresenter;
    private ParentStudentsAdapter parentStudentsAdapter;
    private Boolean exit = false;
    private String currentVersion, appVersion;
    private Button buttonPayToTrack;
    private ArrayList ids = new ArrayList();
    private ArrayList paymentStatusList = new ArrayList();
    private String adminid;
    private ProgressDialog progress;
    private ArrayList<String> academicList, academicIdInList;
    int academicyear;
    private String acdemicName;
    TextView accademic_year1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_parent_students);
        binding = (ActivityParentStudentsBinding) viewDataBinding;
        sharedPref = SharedPref.getmSharedPrefInstance(this);
        buttonPayToTrack = findViewById(R.id.buttonPayToTrack);
        buttonPayToTrack.setVisibility(View.GONE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //crash
        Fabric.with(this, new Crashlytics());

        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setSupportActionBar(toolbar);
//        setupActionBar("CREDO");
        try {
            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
            adminid = preferences.getString(Constants.ADMIN_ID, adminid);
            Log.d("MS",adminid);

//            Utils.showProgressBar(this);
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("parent_id", "" + sharedPref.getParentId());
//            jsonObject.addProperty("academic_year",""+academicyear);
            parentPresenter = new ParentPresenter(this);
//            parentPresenter.onGetParentStudents(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        accademic_year1 = findViewById(R.id.accademic_year1);

        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });



       /* if(!preferences.contains(Constants.DEFAULTACADEMIC_ID)) {
            getAcademic();
        }*/


//        AppUpdater appUpdater = new AppUpdater(this);
//        appUpdater.start();
//
//
//        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
//                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
//                .withListener(new AppUpdaterUtils.UpdateListener() {
//                    @Override
//                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
//                        Log.d("Latest Version", update.getLatestVersion());
//                        Log.d("Latest Version Code", String.valueOf(update.getLatestVersionCode()));
//                        Log.d("Release notes", update.getReleaseNotes());
//                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
//
//                        if (!(isUpdateAvailable)) {
//                            setUpdatePopUp();
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(AppUpdaterError error) {
//                        Log.d("AppUpdater Error", "Something went wrong");
//                    }
//                });
//        appUpdaterUtils.start();


        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density;

        Resources r = getResources();
        float heightPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, r.getDisplayMetrics());
        float widthPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, r.getDisplayMetrics());
        int heightInt = Math.round(heightPX);
        int widthInt = Math.round(widthPX);
        String heightOfScreen = String.valueOf(heightInt);
        String widthOfScreen = String.valueOf(widthInt);


        Log.d("dpHeight", dpHeight + "  ,  " + dpWidth + " , " + heightInt + " , " + heightOfScreen);
//        sp.edit().putString(Constants.HEIGHT_OF_SCREEN, heightOfScreen).apply();
//        sp.edit().putString(Constants.WIDTH_OF_SCREEN, widthOfScreen).apply();
        sharedPref.saveHeight(heightOfScreen);
        sharedPref.saveWidth(widthOfScreen);


        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d("currentVersion", currentVersion + "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //getUpdateDate();
        callDeviceAPI();

        buttonPayToTrack.setOnClickListener(v -> {
            ids = parentStudentsAdapter.getIds();
            Log.d("inFragment", "onClick:   ids       " + ids);
            if (ids.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Select at least one check box", Toast.LENGTH_SHORT).show();
                return;
            }
            int money = parentStudentsAdapter.getAmount();
            showAttendanceDialog(money);


//            ids.clear();
//            ids = parentStudentsAdapter.getIds();
//
//            if (ids!=null){
//
//                if (ids.isEmpty()) {
//                    Toast.makeText(ParentStudentsScreen.this, "Select at least one check box", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Log.d("ids=====>", ids + "");
////                int size = ids.size();
//
//                int money = parentStudentsAdapter.getAmount();
//                Log.d(TAG, "onClick:money======> "+money);
//                showAttendanceDialog(money);
//
//            }


//                Toast.makeText(ParentStudentsScreen.this, "processing....", Toast.LENGTH_SHORT).show();
        });
        Checkout.preload(getApplicationContext());
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
                    dismissLoadingDialog();

                    // accademicResponse(Objects.requireNonNull(response.body()));
                }
                @Override
                public void onFailure(@NonNull Call<AccademicyearResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.IntenetSettings(ParentStudentsScreen.this);
        }
    }

    private void accademicResponse(AccademicyearResponse body) {
        dismissLoadingDialog();
        String response = body.getCode();
        String description = body.getDescription();
        academicyear = Integer.parseInt(String.valueOf(Integer.parseInt(body.getDefault_academic_year())));
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

//            Utils.showProgressBar(this);
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("parent_id", "" + sharedPref.getParentId());
//            jsonObject.addProperty("academic_year",""+academicyear);

//            parentPresenter.onGetParentStudents(jsonObject);
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


    private void showAttendanceDialog(int amount) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Dear Sir/Madam are you sure you want to pay Rs " + (amount));
        builder.setPositiveButton("Yes", (dialog, which) -> {
//                final int amount = size*120;
            StartPayment(amount);
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();


    }

    private void StartPayment(int amount) {
        Log.d(TAG, "StartPayment: 0");
//        /**
//         * Instantiate Checkout
//         */
        Checkout checkout = new Checkout();

//        /**
//         * Set your logo here
//         */
        checkout.setImage(R.drawable.ic_app_logo);

//        /**
//         * Reference to current activity
//         */
        final Activity activity = this;

//        /**
//         * Pass your payment options to the Razorpay Checkout as a JSONObject
//         */
        try {
            JSONObject options = new JSONObject();
//            /**
//             * Merchant Name
//             * eg: Rentomojo || HasGeek etc.
//             */
            options.put("name", sharedPref.getParentName());

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", sharedPref.getMobileNumber());
            options.put("prefill", preFill);
//            /**
//             * Description can be anything
//             * eg: Order #123123
//             *     Invoice Payment
//             *     etc.
//             */
            String orderId = UUID.randomUUID().toString();
            options.put("description", orderId);

            options.put("currency", "INR");
//            /**
//             * Amount is always passed in PAISE
//             * Eg: "500" = Rs 5.00
//             */
            Log.d(TAG, "StartPayment:          " + amount);

//            int amountedfsawf = Integer.parseInt(String.valueOf(amount));
            int am = amount * 100;
            options.put("amount", am);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    private void callDeviceAPI() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = Objects.requireNonNull(task.getResult()).getToken();
                Log.d(TAG, "onComplete: " + token);
            }
        });
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "callDeviceAPI: " + token);
        Log.d(TAG, "sharedPref.getDeviceKey() " + sharedPref.getDeviceKey());
        if (!sharedPref.getDeviceKey().equals("")) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobile", sharedPref.getMobileNumber());
            jsonObject.addProperty("device_token", sharedPref.getDeviceKey());
            parentPresenter.onSendDeviceKey(jsonObject);
        }
    }


    public void onSuccessParentStudents(StudentsResponse studentsResponse) {
        Utils.hideProgressBar();


        if (studentsResponse.getCode() == 200) {
            getPaymentListData(studentsResponse);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ParentStudentsScreen.this,
                    LinearLayoutManager.VERTICAL, false);
            binding.parentStudentList.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.parentStudentList.getContext(),
                    linearLayoutManager.getOrientation());
            binding.parentStudentList.setVisibility(View.VISIBLE);
            parentStudentsAdapter = new ParentStudentsAdapter(this, studentsResponse.getStudentInfos());
//        binding.parentStudentList.addItemDecoration(dividerItemDecoration);
            binding.parentStudentList.setAdapter(parentStudentsAdapter);
            parentStudentsAdapter.notifyDataSetChanged();

        } else {
            buttonPayToTrack.setVisibility(View.GONE);
            Toast.makeText(this, studentsResponse.description, Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpdatePopUp() {

        new AppUpdater(this)
                .setDisplay(com.github.javiersantos.appupdater.enums.Display.DIALOG)
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of my app!")
                .setButtonUpdate("Update now?")
                .setButtonUpdateClickListener((dialog, which) -> {

                })
                .setCancelable(false);
    }

    private void getPaymentListData(StudentsResponse body) {
        List<StudentInfo> list = body.getStudentInfos();

        for (int i = 0; i < list.size(); i++) {

            String paymentIds = list.get(i).getPayment_status();
            paymentStatusList.add(paymentIds);

            if (paymentStatusList.contains("0")) {
                buttonPayToTrack.setVisibility(View.GONE);
            }

        }
    }

    public void onFailureParentStudents(StudentsResponse studentsResponse) {
        Utils.hideProgressBar();
        binding.parentStudentList.setVisibility(View.GONE);
        buttonPayToTrack.setVisibility(View.INVISIBLE);
        Snackbar.make(binding.getRoot(), "" + studentsResponse.getDescription(), Snackbar.LENGTH_LONG).show();
    }

    public void onSuccessParentDeviceToken(JsonObject jsonObject) {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>> " + jsonObject);
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        } else {
//            if (this.drawer.isDrawerOpen(GravityCompat.START)) {
//                this.drawer.closeDrawer(GravityCompat.START);
//            } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(() -> exit = false, 2 * 1000);
        }
//        }
    }

    //
    @Override
    public void onPaymentSuccess(String tnxId) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        paymentUpdateToServer("success", date, tnxId, "1");
    }


    @Override
    public void onPaymentError(int i, String tnxId) {
//        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//        paymentUpdateToServer("failure", date, tnxId, "2");
        Intent in = new Intent(getApplicationContext(), PaymentScreen.class);
        in.putExtra("value", "2");
        startActivity(in);
    }


    private void paymentUpdateToServer(String status, String date, String tnxId, String id) {

        PaymentRequestIds request = new PaymentRequestIds();
        request.setStudents(ids);
        request.setTnxId(tnxId);
        ITutorSource.getRestAPI().updatePaymentIds(request).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse  : " + response);
                    updateStudentIds(Objects.requireNonNull(response.body()), id);
                    Utils.hideProgressBar();
                } else {
                    Utils.hideProgressBar();
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(ParentStudentsScreen.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(ParentStudentsScreen.this, "server broken try again", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ParentStudentsScreen.this, "Un excepted error try again", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Utils.hideProgressBar();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(ParentStudentsScreen.this, "Server internal error try again", Toast.LENGTH_SHORT).show();
                    Snackbar.make(findViewById(android.R.id.content), "Server internal error try again", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
//        Toast.makeText(this, "processing......", Toast.LENGTH_SHORT).show();
    }

    private void updateStudentIds(APIResponse body, String id) {
        String response = String.valueOf(body.getCode());
        String description = body.getDescription();

        if ("200".equals(response)) {
            if (id.equals("1")) {
                Intent in = new Intent(getApplicationContext(), PaymentScreen.class);
                in.putExtra("value", "1");
                startActivity(in);
            } else {
                Intent in = new Intent(getApplicationContext(), PaymentScreen.class);
                in.putExtra("value", "2");
                startActivity(in);
            }
        }
        Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
    }

    private void getUpdateDate() {


        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().appVersionNo().enqueue(new Callback<AppVersionResponse>() {
                @Override
                public void onResponse(@NonNull Call<AppVersionResponse> call, @NonNull Response<AppVersionResponse> response) {
                    Utils.hideProgressBar();
                    appResponse(Objects.requireNonNull(response.body()));
                }

                @Override
                public void onFailure(@NonNull Call<AppVersionResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    if (t instanceof SocketTimeoutException) {
                        Snackbar.make(findViewById(android.R.id.content), "internal error try again", Snackbar.LENGTH_SHORT).show();
                    }
                    Utils.hideProgressBar();
                    Log.d("t========>", t + "t");


                }
            });
        } else {
            Constants.IntenetSettings(ParentStudentsScreen.this);
        }


    }

    private void appResponse(AppVersionResponse body) {

        String response = body.getCode();
        String description = body.getDescription();
        appVersion = body.getAppVersion();
        if ("200".equals(response)) {
            if (appVersion != null && (!(appVersion.equals(currentVersion)))) {
                upgradeVersionDialogBox();
                //Toast.makeText(this, appVersion+"     ,,,      "+currentVersion, Toast.LENGTH_SHORT).show();
            }
//                upgradeVersionDialogBox();
        } else {
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(android.R.id.content), "Error occurred please try again", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void upgradeVersionDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert !!");
        builder.setMessage("Updated version is available in PLAY STORE please update your app");

        builder.setPositiveButton("OK", (dialog, which) -> launchMarket());
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    private void launchMarket() {

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    public void setupActionBar(String feesText) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.parent_student_title_bar);
        View customView = getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0, 0);
        ((TextView) customView.findViewById(R.id.custom_title)).setText(feesText);
        ((TextView) customView.findViewById(R.id.custom_title)).setAllCaps(true);

        customView.findViewById(R.id.back_button)
                .setOnClickListener(v -> onBackPressed());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflatchange_passworder();
        getMenuInflater().inflate(R.menu.my_options_menu, menu);
        return true;
    }


    @Override
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

            //  academicyear=sp.getString(Constants.DEFAULTACADEMIC_ID)
        }
        //    getAcademic();

        // accademic_year1.setText(preferences.getString(Constants.DEFAULTACADEMIC_NAME, "Select"));

        //
        SharedPreferences preferences1=getSharedPreferences(Constants.PREFERENCE_NAME,0);
        academicyear= Integer.parseInt(preferences1.getString(Constants.DEFAULTACADEMIC_ID, String.valueOf(academicyear)));
        Log.d("acy", String.valueOf(academicyear));
        Utils.showProgressBar(this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parent_id", "" + sharedPref.getParentId());
        jsonObject.addProperty("academic_year",""+academicyear);
        parentPresenter = new ParentPresenter(this);
        parentPresenter.onGetParentStudents(jsonObject);

    }
}

