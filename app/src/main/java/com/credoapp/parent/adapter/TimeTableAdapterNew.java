package com.credoapp.parent.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.credoapp.parent.R;
import com.credoapp.parent.model.timeTableModels.TimeTableResults;

import java.util.List;

public class TimeTableAdapterNew extends RecyclerView.Adapter<TimeTableAdapterNew.MyViewHolder> {
    private List<TimeTableResults> timeTableResults;
    private Context mContext;
    public TimeTableAdapterNew(Context applicationContext, List<TimeTableResults> timeTableResults) {
        this.mContext=applicationContext;
        this.timeTableResults=timeTableResults;
    }

    @NonNull
    @Override
    public TimeTableAdapterNew.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_table_adapter, parent, false);

        return new TimeTableAdapterNew.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableAdapterNew.MyViewHolder myViewHolder, int i) {
        TimeTableResults listResults = timeTableResults.get(i);
        myViewHolder.dayTimeAdapter.setText(listResults.getDay());
        myViewHolder.subjectTimeAdapter.setText(listResults.getSubject());
        myViewHolder.timeInTimeAdapter.setText(listResults.getFromTime()+"to"+listResults.getToTime());


    }

    @Override
    public int getItemCount() {
        return timeTableResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dayTimeAdapter,subjectTimeAdapter,timeInTimeAdapter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTimeAdapter = itemView.findViewById(R.id.dayTimeAdapter);
            subjectTimeAdapter = itemView.findViewById(R.id.subjectTimeAdapter);
            timeInTimeAdapter = itemView.findViewById(R.id.timeInTimeAdapter);
        }
    }
}
