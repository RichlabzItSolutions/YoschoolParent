package com.credoapp.parent.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.credoapp.parent.model.AttendanceReport;
import com.credoapp.parent.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    List<AttendanceReport> notificationsResults;
    Context mContext;
    public AttendanceAdapter(Context applicationContext, List<AttendanceReport> notificationsResults) {
        this.mContext = applicationContext;
        this.notificationsResults = notificationsResults;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_list, parent, false);

        return new AttendanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AttendanceReport list = notificationsResults.get(position);
        // holder.titleNotificationAdaptor.setText(list.getTitle());



        Double D = list.getPercentage();
        int i = D.intValue();

        if (i<51){
            holder.percentage.setTextColor(Color.parseColor("#FF000C"));
        }else if (i < 76){
            holder.percentage.setTextColor(Color.parseColor("#ef8e4d"));
        }else {
            holder.percentage.setTextColor(Color.parseColor("#d92201"));
        }
        holder.percentage.setText(String.valueOf(i));

        holder.days_absent.setText(list.getAbsentDays());
        holder.days_present.setText(list.getPresentDays());
        switch (list.getMonth()) {
            case "1":
                holder.month.setText("Jan");
                break;
            case "2":
                holder.month.setText("Feb");
                break;
            case "3":
                holder.month.setText("Mar");
                break;
            case "4":
                holder.month.setText("Apl");
                break;
            case "5":
                holder.month.setText("May");
                break;
            case "6":
                holder.month.setText("Jun");
                break;
            case "7":
                holder.month.setText("Jul");
                break;
            case "8":
                holder.month.setText("Aug");
                break;
            case "9":
                holder.month.setText("Sep");
                break;
            case "10":
                holder.month.setText("Oct");
                break;
            case "11":
                holder.month.setText("Now");
                break;
            case "12":
                holder.month.setText("Dec");
                break;
        }



    }

    @Override
    public int getItemCount() {
        return notificationsResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView month,days_present,days_absent,percentage;
        public MyViewHolder(View itemView) {
            super(itemView);

            percentage = itemView.findViewById(R.id.percentage);
            days_absent = itemView.findViewById(R.id.days_absent);
            days_present = itemView.findViewById(R.id.days_present);
            month = itemView.findViewById(R.id.month);
        }
    }
}
