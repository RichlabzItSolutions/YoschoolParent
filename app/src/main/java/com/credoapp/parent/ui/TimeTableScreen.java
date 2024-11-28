package com.credoapp.parent.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.widget.Toast;


import com.credoapp.parent.fragment.TimeTableFragment;
import com.credoapp.parent.model.ClassTimeTable;
import com.credoapp.parent.model.ClassTimeTableResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;
import com.credoapp.parent.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class TimeTableScreen extends ParentActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = TimeTableScreen.class.getSimpleName();
    StudentInfo studentInfo;
    private ViewPager viewPager;
    private TimeTableFragment mondayTimeTableFragment, tuesdayTimetableFragment,
            wednesdayTimeTableFragment,
            thursdayTimetableFragment, fridayTimeTableFragment, saturdayTimeTableFragment;
    private ClassTimeTableResponse classTimeTableResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_layout);
        setupActionBar("Time Table");
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        JsonObject jsonObject = new JsonObject();
//        {"student_id":1}
        jsonObject.addProperty("student_id", studentInfo.getStudent_id());
        Utils.showProgressBar(this);
        ParentPresenter parentPresenter = new ParentPresenter(this);
        parentPresenter.onGetTimeTableDummy(jsonObject);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mondayTimeTableFragment = new TimeTableFragment();
        tuesdayTimetableFragment = new TimeTableFragment();
        wednesdayTimeTableFragment = new TimeTableFragment();
        thursdayTimetableFragment = new TimeTableFragment();
        fridayTimeTableFragment = new TimeTableFragment();
        saturdayTimeTableFragment = new TimeTableFragment();
        adapter.addFragment(mondayTimeTableFragment, "Monday");
        adapter.addFragment(tuesdayTimetableFragment, "Tuesday");
        adapter.addFragment(wednesdayTimeTableFragment, "Wednesday");
        adapter.addFragment(thursdayTimetableFragment, "Thursday");
        adapter.addFragment(fridayTimeTableFragment, "Friday");
        adapter.addFragment(saturdayTimeTableFragment, "Saturday");
        viewPager.setAdapter(adapter);
        onPageSelected(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (classTimeTableResponse == null) return;
        ClassTimeTable classTimeTable = classTimeTableResponse.getClassTimeTables().get(position);
        if (position == 0) {
            mondayTimeTableFragment.updateMonTimeTableSheet(classTimeTable.getMondayList());
        } else if (position == 1) {
            tuesdayTimetableFragment.updateTueTimeTableSheet(classTimeTable.getTuesdayList());
        } else if (position == 2) {
            wednesdayTimeTableFragment.updateWedTimeTableSheet(classTimeTable.getWednesdayList());
        } else if (position == 3) {
            thursdayTimetableFragment.updateThuTimeTableSheet(classTimeTable.getThursdayList());
        } else if (position == 4) {
            fridayTimeTableFragment.updateFriTimeTableSheet(classTimeTable.getFridayList());
        } else if (position == 5) {
            saturdayTimeTableFragment.updateSatTimeTableSheet(classTimeTable.getSaturdayList());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onSuccessTimeTable(ClassTimeTableResponse classTimeTableResponse) {
        Utils.hideProgressBar();
        this.classTimeTableResponse = classTimeTableResponse;
        Log.d(TAG, "dummyTimeTable " + classTimeTableResponse);
        if (classTimeTableResponse.code == 204) {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
            return;
        }
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Monday"));
        tabLayout.addTab(tabLayout.newTab().setText("Tuesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Wednesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Thursday"));
        tabLayout.addTab(tabLayout.newTab().setText("Friday"));
        tabLayout.addTab(tabLayout.newTab().setText("Saturday"));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
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

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
