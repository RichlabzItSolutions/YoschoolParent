package com.credoapp.parent.views.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.credoapp.parent.R;
import com.credoapp.parent.views.CalendarView;
import com.credoapp.parent.views.EventDay;
import com.credoapp.parent.views.utils.CalendarProperties;
import com.credoapp.parent.views.utils.DateUtils;
import com.credoapp.parent.views.utils.DayColorsUtils;
import com.credoapp.parent.views.utils.SelectedDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


class CalendarDayAdapter extends ArrayAdapter<Date> {
    private static final String TAG = CalendarDayAdapter.class.getSimpleName();
    private CalendarPageAdapter mCalendarPageAdapter;
    private LayoutInflater mLayoutInflater;
    private int mPageMonth;
    private Calendar mToday = DateUtils.getCalendar();

    private CalendarProperties mCalendarProperties;
    Context mContext;
    List<EventDay> eventDays;

    CalendarDayAdapter(CalendarPageAdapter calendarPageAdapter, Context context, CalendarProperties calendarProperties,
                       ArrayList<Date> dates, int pageMonth) {
        super(context, calendarProperties.getItemLayoutResource(), dates);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
        eventDays = calendarProperties.getEventDays();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getItemLayoutResource(), parent, false);
        }
        TextView dayLabel = view.findViewById(R.id.dayLabel);
        LinearLayout day_layout = view.findViewById(R.id.day_layout);

        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));


        setLabelColors(dayLabel, day);

        dayLabel.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
        // Loading an image of the event
        if (day_layout != null) {
            loadIcon(dayLabel, day);
        }

        return view;
    }

    private void setLabelColors(TextView dayLabel, Calendar day) {
        // Setting not current month day color
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }
        // Set view for all SelectedDays
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(dayLabel));

            DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
            return;
        }

        // Setting disabled days color
        if (!isActiveDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getDisabledDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        // Setting current month day color
        DayColorsUtils.setCurrentMonthDayColors(day, mToday, dayLabel, mCalendarProperties);
    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth &&
                !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                        || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private boolean isActiveDay(Calendar day) {
        return !mCalendarProperties.getDisabledDays().contains(day);
    }

    private void loadIcon(TextView day_layout, Calendar day) {
        if (mCalendarProperties.getEventDays() == null || mCalendarProperties.getCalendarType() != CalendarView.CLASSIC) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                day_layout.setBackground(null);
            } else {
                day_layout.setBackgroundDrawable(null);
            }
            return;
        }

        for (int i = 0; i < mCalendarProperties.getEventDays().size(); i++) {
            if (mCalendarProperties.getEventDays().get(i).getCalendar().getTimeInMillis() == day.getTimeInMillis()) {
                day_layout.setBackgroundResource(mCalendarProperties.getEventDays().get(i).getImageResource());
                break;
            }
        }


//        Stream.of(mCalendarProperties.getEventDays()).filter(eventDate ->
//                eventDate.getCalendar().equals(day)).findFirst().executeIfPresent(eventDay -> {
//            Log.d(TAG, "getCalendar 11111111111 " + eventDay.getCalendar().get(Calendar.DATE) + " eventDay.getImageResource() " + eventDay.getImageResource());
////            ImageUtils.loadResource(dayIcon, eventDay.getImageResource());
//            day_layout.setBackgroundResource(eventDay.getImageResource());
//            // If a day doesn't belong to current month then image is transparent
//            if (!isCurrentMonthDay(day) || !isActiveDay(day)) {
////                dayIcon.setAlpha(0.12f);
//            }
//
//        });
    }
}
