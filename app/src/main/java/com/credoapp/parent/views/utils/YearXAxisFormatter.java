package com.credoapp.parent.views.utils;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Calendar;

public class YearXAxisFormatter implements IAxisValueFormatter {

    private static final String TAG = YearXAxisFormatter.class.getSimpleName();
    private Calendar currentCalendar;
//    protected String[] mMonths = new String[]{
//            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
//    };

    protected String[] mMonths = new String[]{
            "Dec", "Nov", "Oct", "Sep", "Aug", "Jul", "Jun", "May", "Apr", "Mar", "Feb", "Jan"
    };
    private int monthsCount;

    public YearXAxisFormatter(int monthsCount) {
        // maybe do something here or provide parameters in constructor
        this.monthsCount = monthsCount;
    }

    public YearXAxisFormatter(Calendar currentCalendar, int monthsCount) {
        this.currentCalendar = currentCalendar;
        this.monthsCount = monthsCount;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        float percent = value / axis.mAxisRange;
//        Log.d(TAG, "percent " + percent);
        return mMonths[(int) (monthsCount * percent)];
    }
}
