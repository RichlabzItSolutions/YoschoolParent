package com.credoapp.parent.adapter;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.credoapp.parent.R;
import com.credoapp.parent.model.TimeTable;
import com.credoapp.parent.databinding.ItemExamDateBinding;

import java.util.List;

public class ExamDatesAdapter extends RecyclerView.Adapter<ExamDatesAdapter.ViewHolder> {
    private Activity activity;
    private List<TimeTable> examList;

    public ExamDatesAdapter(Activity activity, List<TimeTable> examList) {
        this.activity = activity;
        this.examList = examList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),
                R.layout.item_exam_date, parent, false);
        ItemExamDateBinding binding = (ItemExamDateBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeTable exam = examList.get(position);
        holder.binding.subjectNameText.setText(exam.getSubject());
        holder.binding.dateAndTimeText.setText(exam.getStart_date()+"\n"+exam.getFromTime()+" to "+exam.getToTime());
        holder.binding.totalMarks.setText(exam.getTotalMarks());
        holder.binding.obtainedMarks.setText(exam.getGainedMarks());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemExamDateBinding binding;

        public ViewHolder(ItemExamDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
