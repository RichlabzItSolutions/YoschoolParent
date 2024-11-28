package com.credoapp.parent.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.credoapp.parent.R;
import com.credoapp.parent.model.holidaysModels.HolidaysListResults;
import com.credoapp.parent.ui.HolidaysListScreen;
import java.util.List;

public class HolidaysListAdapter extends RecyclerView.Adapter<HolidaysListAdapter.MyViewHolder> {

    private final List<HolidaysListResults> holidaysListResults;
    private final Context mContext;

    public HolidaysListAdapter(HolidaysListScreen holidaysListScreen, List<HolidaysListResults> holidaysListResults) {
        this.holidaysListResults = holidaysListResults;
        this.mContext = holidaysListScreen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holidys_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HolidaysListResults list = holidaysListResults.get(position);
        if (list.getFromDate().equals(list.getToDate())) {
            holder.textDateOfHoliday.setText(getFormattedDateText("Date :- ", list.getFromDate()));
            holder.textendDateOfHoliday.setVisibility(View.GONE); // Hide endDate TextView
        } else {
            holder.textDateOfHoliday.setText(getFormattedDateText("From Date : ", list.getFromDate()));
            holder.textendDateOfHoliday.setText(getFormattedDateText("To Date : ", list.getToDate()));
            holder.textendDateOfHoliday.setVisibility(View.VISIBLE); // Show endDate TextView
        }
        holder.textHolidayFor.setText(list.getDescriptionOfHoliday());
    }

    // Helper method to format date text with bold formatting
    private SpannableStringBuilder getFormattedDateText(String prefix, String date) {
        SpannableStringBuilder sb = new SpannableStringBuilder(prefix + date);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        // Apply bold formatting to the prefix only
        sb.setSpan(boldSpan, 0, prefix.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sb;
    }


    @Override
    public int getItemCount() {
        return holidaysListResults.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textDateOfHoliday, textHolidayFor, textendDateOfHoliday;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textHolidayFor = itemView.findViewById(R.id.textHolidayFor);
            textDateOfHoliday = itemView.findViewById(R.id.textDateOfHoliday);
            textendDateOfHoliday = itemView.findViewById(R.id.textendDateOfHoliday);
        }
    }
}