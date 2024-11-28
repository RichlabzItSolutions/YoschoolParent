package com.credoapp.parent.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.credoapp.parent.R;
import com.credoapp.parent.model.DayTimeTable;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {

    private Activity timeTableScreen;
    private List<DayTimeTable> classTimeTables;

    public TimeTableAdapter(Activity timeTableScreen, List<DayTimeTable> classTimeTables) {
        this.timeTableScreen = timeTableScreen;
        this.classTimeTables = classTimeTables;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayTimeTable dayTimeTable = classTimeTables.get(position);
        holder.subject_text.setText(dayTimeTable.getSubject());
        holder.time_text.setText(dayTimeTable.getTime());
    }

    @Override
    public int getItemCount() {
        return classTimeTables.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_text, subject_text;

        public ViewHolder(View itemView) {
            super(itemView);
            time_text = itemView.findViewById(R.id.time_text);
            subject_text = itemView.findViewById(R.id.subject_text);
        }
    }
}
