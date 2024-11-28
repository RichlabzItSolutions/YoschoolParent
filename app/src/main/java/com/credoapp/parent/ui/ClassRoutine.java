package com.credoapp.parent.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.credoapp.parent.R;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.fragment.FridayFragment;
import com.credoapp.parent.fragment.MondayFragment;
import com.credoapp.parent.fragment.SaturdayFragment;
import com.credoapp.parent.fragment.ThursdayFragment;
import com.credoapp.parent.fragment.TuesdayFragment;
import com.credoapp.parent.fragment.WednesdayFragment;
import com.credoapp.parent.model.StudentInfo;


import java.util.ArrayList;
import java.util.List;

public class ClassRoutine extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    StudentInfo studentInfo;
    private String TAG = ClassRoutine.class.getSimpleName();
    String adminId,class_id,studentId;
    Bundle bundle = new Bundle();
    private String acdemicName;
    TextView accademic_year1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_routine);
        Toolbar toolbar = findViewById(R.id.toolbar);
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        class_id = getIntent().getStringExtra("classId");
        studentId = getIntent().getStringExtra("student_id");

        LinearLayout backLayoutNotifications = findViewById(R.id.backLayoutclassroutine);
        backLayoutNotifications.setOnClickListener(view -> finish());

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        accademic_year1 = findViewById(R.id.accademic_year1);
        accademic_year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectAcademic.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        acdemicName = sp.getString(Constants.DEFAULTACADEMIC_NAME, acdemicName);
        accademic_year1.setText(acdemicName);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterr adapter = new ViewPagerAdapterr(getSupportFragmentManager());


        MondayFragment mondayFragment = new MondayFragment();
        bundle.putString("adminId", adminId);
        bundle.putString("studentId", studentId);
        bundle.putString("classId", class_id);
        mondayFragment.setArguments(bundle);
        adapter.addFragment(mondayFragment, "   Monday   ");

        TuesdayFragment tuesdayFragment = new TuesdayFragment();
        bundle.putString("adminId", adminId);
        bundle.putString("studentId", studentId);
        bundle.putString("classId", class_id);
        tuesdayFragment.setArguments(bundle);
        adapter.addFragment(tuesdayFragment, "   Tuesday   ");

        WednesdayFragment wednesdayFragment = new WednesdayFragment();
        bundle.putString("adminId", adminId);
        bundle.putString("studentId", studentId);
        bundle.putString("classId", class_id);
        wednesdayFragment.setArguments(bundle);
        adapter.addFragment(wednesdayFragment, "   Wednesday   ");

        ThursdayFragment thursdayFragment = new ThursdayFragment();
        bundle.putString("adminId", adminId);
        bundle.putString("studentId", studentId);
        bundle.putString("classId", class_id);
        thursdayFragment.setArguments(bundle);
        adapter.addFragment(thursdayFragment, "   Thursday   ");

        FridayFragment fridayFragment = new FridayFragment();
        bundle.putString("adminId", adminId);
        bundle.putString("studentId", studentId);
        bundle.putString("classId", class_id);
        fridayFragment.setArguments(bundle);
        adapter.addFragment(fridayFragment, "   Friday   ");

        SaturdayFragment saturdayFragment = new SaturdayFragment();
        bundle.putString("adminId", adminId);
        bundle.putString("studentId", studentId);
        bundle.putString("classId", class_id);
        saturdayFragment.setArguments(bundle);
        adapter.addFragment(saturdayFragment, "   Saturday   ");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class ViewPagerAdapterr extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapterr(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    private void getClassRoutineData() {
//        Utils.showProgressBar(this);
//        ThreeMonthsDataRequest request = new ThreeMonthsDataRequest();
//        request.setStudent_id(studentInfo.getStudent_id());
//        ITutorSource.getRestAPI().classRoutine(request).enqueue(new Callback<ClassRoutineResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<ClassRoutineResponse> call, @NonNull Response<ClassRoutineResponse> response) {
//
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "onResponse  : " + response);
//                    getClassRoutine(Objects.requireNonNull(response.body()));
//                    Utils.hideProgressBar();
//                }else {
//                    Utils.hideProgressBar();
//                    switch (response.code()) {
//                        case 404:
//                            Toast.makeText(ClassRoutine.this, "not found", Toast.LENGTH_SHORT).show();
//                            break;
//                        case 500:
//                            Toast.makeText(ClassRoutine.this, "server broken try again", Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            Toast.makeText(ClassRoutine.this, "Un excepted error try again", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                }
//
//            }
//            @Override
//            public void onFailure(@NonNull Call<ClassRoutineResponse> call, @NonNull Throwable t) {
//                t.printStackTrace();
//                Utils.hideProgressBar();
//                if (t instanceof SocketTimeoutException){
//                    Toast.makeText(ClassRoutine.this, "Server internal error try again", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(findViewById(android.R.id.content),"Server internal error try again",Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }

//    private void getClassRoutine(ClassRoutineResponse body) {
//
//        Utils.hideProgressBar();
//        String response = String.valueOf(body.getCode());
//        String description = body.getDescription();
//
//        switch (response) {
//            case "200":
//
//                break;
//            case "204":
//                Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
//                break;
//            default:
//                Snackbar.make(findViewById(android.R.id.content),description, Snackbar.LENGTH_SHORT).show();
//                break;
//        }
//    }


    @Override
    public void onBackPressed(){
//        Intent in = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(in);
//        finish();
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    @Override
    public boolean onSupportNavigateUp() {
//        Intent in = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(in);
//        finish();
        onBackPressed();
        return true;
    }
}
