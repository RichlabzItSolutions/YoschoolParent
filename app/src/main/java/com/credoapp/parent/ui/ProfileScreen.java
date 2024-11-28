package com.credoapp.parent.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.credoapp.parent.fragment.ParentInfoFragment;
import com.credoapp.parent.fragment.StudentInfoFragment;
import com.credoapp.parent.model.ParentStudentResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;
import com.credoapp.parent.R;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileScreen extends ParentActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = ProfileScreen.class.getSimpleName();
    private ViewPager viewPager;
    private StudentInfo studentInfo;
    private TextView profile_name_text;
    private ImageView profile_icon;
    TextView student_roll_number;
    private ParentStudentResponse parentStudentResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        setupActionBar("Profile");
        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        viewPager = findViewById(R.id.viewPager);
        profile_name_text = findViewById(R.id.profile_name_text);
        profile_icon = findViewById(R.id.profile_icon);
        student_roll_number = findViewById(R.id.student_roll_number);
        setupViewPager(viewPager);
        try {
            Utils.showProgressBar(this);
            ParentPresenter parentPresenter = new ParentPresenter(this);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("parent_id", sharedPref.getParentId());
            jsonObject.addProperty("student_id", studentInfo.getStudent_id());
            parentPresenter.onGetStudentParentProfile(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Student Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Parent Info"));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);
    }

    StudentInfoFragment studentInfoFragment;
    ParentInfoFragment parentInfoFragment;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        studentInfoFragment = new StudentInfoFragment();
        parentInfoFragment = new ParentInfoFragment();
        adapter.addFragment(studentInfoFragment, "Student Info");
        adapter.addFragment(parentInfoFragment, "Parent Info");
        viewPager.setAdapter(adapter);
    }

    public void onSuccessParentStudentProfile(ParentStudentResponse parentStudentResponse) {
        this.parentStudentResponse = parentStudentResponse;
        Utils.hideProgressBar();
        Log.d(TAG, "onSuccessParentStudentProfile " + parentStudentResponse + " viewPager.getCurrentItem() "
                + viewPager.getCurrentItem());
        profile_name_text.setText(parentStudentResponse.getStudentInfo().getName());
        try {
            Glide.with(this).load(parentStudentResponse.getStudentInfo().getPhoto()).into(profile_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (viewPager.getCurrentItem() == 0) {
            studentInfoFragment.updateStudentProfile(parentStudentResponse);
        } else if (viewPager.getCurrentItem() == 1) {
            parentInfoFragment.updateParentProfile(parentStudentResponse);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (parentStudentResponse == null) return;
        if (position == 0) {
            studentInfoFragment.updateStudentProfile(parentStudentResponse);
        } else if (position == 1) {
            parentInfoFragment.updateParentProfile(parentStudentResponse);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
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
