package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.credoapp.parent.R;
import com.credoapp.parent.model.Exam;
import com.credoapp.parent.databinding.ItemExamDateSubjectBinding;

import java.util.ArrayList;
import java.util.List;

public class ExamSubjectAdapter extends RecyclerView.Adapter<ExamSubjectAdapter.ViewHolder> {


    private final Activity activity;
    private final List<Exam> examList;
    private ArrayList<String> exampleList =new ArrayList<>();
    public ExamSubjectAdapter(Activity activity, List<Exam> examList) {
        this.activity = activity;
        this.examList = examList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_exam_date_subject, parent, false);
        ItemExamDateSubjectBinding binding = (ItemExamDateSubjectBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.binding.semText.setText("" + exam.getExam_category());

//        if (exam.getExam_category().equals("Mid-1")){
//            return;
//        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        holder.binding.examDatesSubjectList.setLayoutManager(linearLayoutManager);
        ExamDatesAdapter examDatesAdapter = new ExamDatesAdapter(activity, exam.getTimeTables());
        holder.binding.examDatesSubjectList.setAdapter(examDatesAdapter);

        exampleList.add(exam.getExam_category());

    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemExamDateSubjectBinding binding;

        public ViewHolder(ItemExamDateSubjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            binding.dateText.setOnClickListener(v -> {
//                Exam exam = examList.get(getAdapterPosition());
//                if (exam.viewing) {
//                    binding.examDatesSubjectList.setVisibility(View.GONE);
//                } else {
//                    binding.examDatesSubjectList.setVisibility(View.VISIBLE);
//                }
//                exam.setViewing(!exam.viewing);
//                examList.set(getAdapterPosition(), exam);
//            });
        }


    }


}
