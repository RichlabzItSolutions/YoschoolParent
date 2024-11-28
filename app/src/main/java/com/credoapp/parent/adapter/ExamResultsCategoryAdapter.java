package com.credoapp.parent.adapter;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.credoapp.parent.R;
import com.credoapp.parent.model.ExamResults;
import com.credoapp.parent.databinding.ItemExamCategoryResultBinding;

import java.util.List;

public class ExamResultsCategoryAdapter extends RecyclerView.Adapter<ExamResultsCategoryAdapter.ViewHolder> {


    private final Activity activity;
    private final List<ExamResults> examList;

    public ExamResultsCategoryAdapter(Activity activity, List<ExamResults> examList) {
        this.activity = activity;
        this.examList = examList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_exam_category_result, parent, false);
        ItemExamCategoryResultBinding binding = (ItemExamCategoryResultBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamResults exam = examList.get(position);
        holder.binding.semText.setText("" + exam.getExam_category());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        holder.binding.examDatesSubjectList.setLayoutManager(linearLayoutManager);
        ExamResultAdapter examDatesAdapter = new ExamResultAdapter(activity, exam.getTimeTables());
        holder.binding.examDatesSubjectList.setAdapter(examDatesAdapter);
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemExamCategoryResultBinding binding;

        public ViewHolder(ItemExamCategoryResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
