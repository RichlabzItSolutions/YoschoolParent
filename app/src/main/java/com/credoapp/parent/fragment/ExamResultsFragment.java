package com.credoapp.parent.fragment;


import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.ExamResultsCategoryAdapter;
import com.credoapp.parent.databinding.ExamDatesFragmentBinding;
import com.credoapp.parent.model.ExamResultsResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.utils.Utils;

import org.parceler.Parcels;

public class ExamResultsFragment extends Fragment {
    StudentInfo studentInfo;
    private ExamDatesFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.exam_dates_fragment, container, false);
        binding = (ExamDatesFragmentBinding) viewDataBinding;
        Bundle arguments = getArguments();
        studentInfo = Parcels.unwrap(arguments.getParcelable("student_info"));
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            StudentPresenter studentPresenter = new StudentPresenter(this);
            Utils.showProgressBar(getActivity());
            studentPresenter.onExamResults();
        }
    }

    public void onSuccessExamResults(ExamResultsResponse examResultsResponse) {
        Utils.hideProgressBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.examDatesList.setLayoutManager(linearLayoutManager);
        ExamResultsCategoryAdapter examDatesAdapter = new ExamResultsCategoryAdapter(getActivity(), examResultsResponse.getExamTimeTableResponse().getExamList());
        binding.examDatesList.setAdapter(examDatesAdapter);
    }
}
