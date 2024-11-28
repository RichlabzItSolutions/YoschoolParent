package com.credoapp.parent.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ActivityExamTimeTableBinding;
import com.credoapp.parent.fragment.ExamDateFragment;
import com.credoapp.parent.fragment.ExamResultsFragment;
import com.credoapp.parent.model.StudentInfo;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ExamTimeTableResultsActivity extends ParentActivity {
    private ActivityExamTimeTableBinding binding;
    StudentInfo studentInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_exam_time_table);
        binding = (ActivityExamTimeTableBinding) viewDataBinding;
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);
        setupActionBar("Exam Time Table");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ExamDateFragment examDateFragment = new ExamDateFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("student_info", Parcels.wrap(studentInfo));
        examDateFragment.setArguments(bundle);
        adapter.addFragment(examDateFragment, "Dates");

        ExamResultsFragment examResultsFragment = new ExamResultsFragment();
        examResultsFragment.setArguments(bundle);
        adapter.addFragment(examResultsFragment, "Results");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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
}
