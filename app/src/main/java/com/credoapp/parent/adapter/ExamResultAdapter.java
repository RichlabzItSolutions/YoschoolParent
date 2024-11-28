package com.credoapp.parent.adapter;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.credoapp.parent.R;
import com.credoapp.parent.model.ExamResult;

import java.util.List;

public class ExamResultAdapter extends RecyclerView.Adapter<ExamResultAdapter.ViewHolder> {
    private Activity activity;
    private List<ExamResult> examList;

    public ExamResultAdapter(Activity activity, List<ExamResult> examList) {
        this.activity = activity;
        this.examList = examList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_result_exam, parent, false);
//        ItemResultExamBinding binding = (ItemResultExamBinding) viewDataBinding;
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamResult exam = examList.get(position);
        holder.subjectNameText.setText(exam.getSubject());
        holder.dateText.setText(exam.getMarks());
//        holder.binding.timeText.setText(exam.getComment());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        TextView subjectNameText, dateText;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            subjectNameText = binding.getRoot().findViewById(R.id.subject_name_text);
            dateText = binding.getRoot().findViewById(R.id.date_text);
        }
    }
}
