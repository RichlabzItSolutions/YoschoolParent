package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.credoapp.parent.R;
import com.credoapp.parent.model.classRoutineModels.ClassRoutineResults;

import java.util.List;

public class ClassRoutineAdapter extends RecyclerView.Adapter<ClassRoutineAdapter.MyViewHolder> {
    private List<ClassRoutineResults> classRoutineResults;
    public ClassRoutineAdapter(FragmentActivity activity, List<ClassRoutineResults> classRoutineResults) {
        this.classRoutineResults = classRoutineResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_routine_adapter, parent, false);
        return new ClassRoutineAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClassRoutineResults listResult = classRoutineResults.get(position);

        holder.fromToTimeClassRou.setText(listResult.getFromTime()+" to "+listResult.getToTime());
        holder.subjectNameClassRou.setText(listResult.getSubject());
    }

    @Override
    public int getItemCount() {
        return classRoutineResults.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fromToTimeClassRou,subjectNameClassRou;
        MyViewHolder(View itemView) {
            super(itemView);
            fromToTimeClassRou = itemView.findViewById(R.id.fromToTimeClassRou);
            subjectNameClassRou = itemView.findViewById(R.id.subjectNameClassRou);
        }
    }
}
