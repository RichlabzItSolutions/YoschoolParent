package com.credoapp.parent.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.credoapp.parent.common.Constants;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.credoapp.parent.adapter.AttendanceAdapter;
import com.credoapp.parent.model.Attendance;
import com.credoapp.parent.model.AttendanceReport;
import com.credoapp.parent.model.AttendanceReportResponse;
import com.credoapp.parent.model.AttendanceResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.attendanceModels.ThreeMonthsDataRequest;
import com.credoapp.parent.model.attendanceModels.ThreeMonthsDataResponse;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.utils.Utils;
import com.credoapp.parent.views.EventDay;
import com.credoapp.parent.views.utils.MyAxisValueFormatter;
import com.credoapp.parent.views.utils.OutOfDateRangeException;
import com.credoapp.parent.views.utils.YearXAxisFormatter;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.JsonObject;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ActivityAttendanceScreenBinding;

import org.parceler.Parcels;

import java.net.SocketTimeoutException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceScreen extends ParentActivity {

    private static final String TAG = AttendanceScreen.class.getSimpleName();
    private ActivityAttendanceScreenBinding binding;

    private ArrayList<Integer> yearsList = new ArrayList<>();
    private List<AttendanceReport> notificationsResults = new ArrayList<>();
    private ArrayList<String> monthsList;
    AttendanceAdapter mAdapter;
    Calendar currentCalendar;
    private final int itemcount = 12;
    StudentInfo studentInfo;
    private StudentPresenter studentPresenter;
    private String acdemicName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_attendance_screen);
        setupActionBar("Attendance");
        binding = (ActivityAttendanceScreenBinding) viewDataBinding;
        currentCalendar = Calendar.getInstance();
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        studentPresenter = new StudentPresenter(this);

        //{"student_id":"1","month":"07","year":"2018"}
        //        requestForAttendance((currentCalendar.get(Calendar.MONTH) + 1) > 9 ? "" +
        // (currentCalendar.get(Calendar.MONTH) + 1) : ("0" + currentCalendar.get(Calendar.MONTH) + 1));

        monthsList = new ArrayList<>(Arrays.asList(new DateFormatSymbols().getMonths()));
        setYearList();
        try {
            Log.d(TAG, " currentCalendar.get(Calendar.MONTH) " + currentCalendar.get(Calendar.MONTH));
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    yearsList);
            binding.yearsSpinner.setAdapter(adapter);
            binding.yearsSpinner.setSelection(yearsList.indexOf(currentCalendar.get(Calendar.YEAR)));
            ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    monthsList);
            binding.calendarView.setDate(currentCalendar);
            binding.calendarView.setMaximumDate(currentCalendar);
            Calendar currentPageDate = binding.calendarView.getCurrentPageDate();
            binding.monthsSpinner.setAdapter(monthsAdapter);
            binding.monthsSpinner.setSelection(currentCalendar.get(Calendar.MONTH));
            binding.monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "position : " + position);
                    try {
//                        requestForAttendance((position + 1 > 9) ? ("" + (position + 1)) : ("0" + (position + 1)));
                        requestForAttendance((position > 9) ? ("" + (position)) : ("0" + (position)));
                        currentCalendar.set(currentCalendar.get(Calendar.YEAR), position, currentCalendar.get(Calendar.DATE));
                        binding.calendarView.setDate(currentCalendar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            binding.holidaysLayoutAttendance.setOnClickListener(v -> {
                Intent in = new Intent(getApplicationContext(),HolidaysListScreen.class);
                startActivity(in);
            });


            binding.yearsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getSelectedItem().equals(currentCalendar.get(Calendar.YEAR))) {
                        Log.d(TAG, "currentPageDate " + currentPageDate.get(Calendar.MONTH));
                        for (int i = 11; i >= currentPageDate.get(Calendar.MONTH) + 1; i--) {
                            Log.d(TAG, "currentPageDate iiiiiiiiiii " + i);
                            monthsList.remove(i);
                        }
                    } else {
                        monthsList.clear();
                        monthsList.addAll(new ArrayList<>(Arrays.asList(new DateFormatSymbols().getMonths())));
                    }
                    monthsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
        setBarchart();

        //getLastThreeAttendanceReport();

    }


    private void getLastThreeAttendanceReport() {
        Utils.showProgressBar(this);
        ThreeMonthsDataRequest request =  new ThreeMonthsDataRequest();
        request.setStudent_id(studentInfo.getStudent_id());
        ITutorSource.getRestAPI().lastThreeMonthsAttendanceResponse(request).enqueue(new Callback<ThreeMonthsDataResponse>() {
            @Override
            public void onResponse(@NonNull Call<ThreeMonthsDataResponse> call, @NonNull Response<ThreeMonthsDataResponse> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse  : " + response);
                    getLastThreeMonthsData(Objects.requireNonNull(response.body()));
                    Utils.hideProgressBar();
                }else {
                    Utils.hideProgressBar();
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(AttendanceScreen.this, response.message(), Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(AttendanceScreen.this, response.message(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(AttendanceScreen.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }
            @Override
            public void onFailure(@NonNull Call<ThreeMonthsDataResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Utils.hideProgressBar();
                Toast.makeText(AttendanceScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content),t.getMessage(),Snackbar.LENGTH_SHORT).show();
//                if (t instanceof SocketTimeoutException){
//
//
//                }else {
//                    Toast.makeText(AttendanceScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                }

            }
        });


//        Utils.showProgressBar(this);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("student_id", "" + studentInfo.getStudent_id());
//        studentPresenter.onGetLastThreeMonthsAttedanceReport(jsonObject);
    }

    private void getLastThreeMonthsData(ThreeMonthsDataResponse body) {
        Utils.hideProgressBar();
        String response = String.valueOf(body.getCode());
        String description = body.getDescription();

        if ("200".equals(response)) {
            binding.recyclerViewAttendanceReport.setVisibility(View.VISIBLE);
            binding.errorTextForLastThreeMonths.setVisibility(View.GONE);
            mAdapter = new AttendanceAdapter(getApplicationContext(), notificationsResults);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            binding.recyclerViewAttendanceReport.setLayoutManager(mLayoutManager);
            binding.recyclerViewAttendanceReport.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewAttendanceReport.setAdapter(mAdapter);
            notificationsResults.clear();
            notificationsResults.addAll(body.getAttendanceReports());
            mAdapter.notifyDataSetChanged();
        } else {
            binding.recyclerViewAttendanceReport.setVisibility(View.GONE);
            binding.errorTextForLastThreeMonths.setVisibility(View.VISIBLE);
            binding.errorTextForLastThreeMonths.setText(description);
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setBarchart() {
        binding.combinedChartView.setDrawBarShadow(false);
        binding.combinedChartView.setDrawValueAboveBar(true);
        binding.combinedChartView.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        binding.combinedChartView.setMaxVisibleValueCount(50);
        // scaling can now only be done on x- and y-axis separately
        binding.combinedChartView.setPinchZoom(false);
        binding.combinedChartView.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
        IAxisValueFormatter xAxisFormatter = new YearXAxisFormatter(currentCalendar, 3);

        XAxis xAxis = binding.combinedChartView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.mAxisRange = 20f;
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(3);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis yAxis = binding.combinedChartView.getAxisLeft();
        yAxis.setLabelCount(8, false);
        yAxis.setValueFormatter(custom);
        binding.combinedChartView.getAxisRight().setEnabled(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(15f);

        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        YAxis rightAxis = binding.combinedChartView.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(12f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = binding.combinedChartView.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(12f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

//        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
//        mv.setChartView(binding.combinedChartView); // For bounds control
//        binding.combinedChartView.setMarker(mv); // Set the marker to the chart
    }

    private void requestForAttendance(String month) {
        Utils.showProgressBar(this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("student_id", studentInfo.getStudent_id());
        jsonObject.addProperty("month", Integer.parseInt(month) + 1);
        jsonObject.addProperty("year", currentCalendar.get(Calendar.YEAR));
        studentPresenter.onRequestAttendance(jsonObject);
    }


    public void onSuccessLastThreeMonthsReports(AttendanceReportResponse attendanceReportResponse) {
        Utils.hideProgressBar();
        if (attendanceReportResponse.code==200){
            Log.d(TAG, "jsonObject " + attendanceReportResponse);

            setData(3, 100, attendanceReportResponse.getAttendanceReports());
            binding.combinedChartView.getData().notifyDataChanged();
            binding.combinedChartView.notifyDataSetChanged();
        }else {
            Log.d("false response===>",attendanceReportResponse.getDescription());
            Snackbar.make(findViewById(android.R.id.content), attendanceReportResponse.description, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setData(int count, float range, List<AttendanceReport> attendanceReports) {
        float start = 1f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.ic_edit)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }
        BarDataSet set1;

        if (binding.combinedChartView.getData() != null &&
                binding.combinedChartView.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) binding.combinedChartView.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            binding.combinedChartView.getData().notifyDataChanged();
            binding.combinedChartView.notifyDataSetChanged();
        } else {

            List<BarEntry> entries = new ArrayList<>();
            for (int i = 0; i < attendanceReports.size(); i++) {
                float data = (float) attendanceReports.get(i).percentage;
                Log.d(TAG, "attendanceReports " + data);
                entries.add(new BarEntry(i, data));
            }
//            entries.add(new BarEntry(0f, 30f));
//            entries.add(new BarEntry(1f, 80f));
//            entries.add(new BarEntry(2f, 60f));

//            entries.add(new BarEntry(3f, 50f));
//            // gap of 2f
//            entries.add(new BarEntry(5f, 70f));
//            entries.add(new BarEntry(6f, 60f));

            BarDataSet set = new BarDataSet(entries, "Last 3 months attendance report");
            BarData data = new BarData(set);
            data.setBarWidth(0.3f); // set custom bar width
            data.setHighlightEnabled(false);
            binding.combinedChartView.setData(data);
            binding.combinedChartView.getData().notifyDataChanged();
            binding.combinedChartView.notifyDataSetChanged();

//            set1 = new BarDataSet(yVals1, null);
//
//            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

//            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
//            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
//            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
//            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
//            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
//            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
//            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
//            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
//
//            List<GradientColor> gradientColors = new ArrayList<>();
//            gradientColors.add(new GradientColor(startColor1, endColor1));
//            gradientColors.add(new GradientColor(startColor2, endColor2));
//            gradientColors.add(new GradientColor(startColor3, endColor3));
//            gradientColors.add(new GradientColor(startColor4, endColor4));
//            gradientColors.add(new GradientColor(startColor5, endColor5));

//            set1.setGradientColors(gradientColors);

//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setBarWidth(0.5f);


        }
    }

    @SuppressLint("SetTextI18n")
    public void onSuccessAttendance(AttendanceResponse attendanceResponse) {
        Utils.hideProgressBar();
        if (attendanceResponse.getCode()==200){
            try {

                Log.d(TAG, "attendance Response : " + attendanceResponse);

                binding.presentText.setText("" + attendanceResponse.getPresent());
                binding.holidayText.setText("" + attendanceResponse.getHoliday());
                binding.leaveText.setText("" + attendanceResponse.getAbsent());


                ArrayList<EventDay> eventDays = new ArrayList<>();

                if (attendanceResponse.getAttendanceList() == null) {
                    Log.d(TAG, "onSuccessAttendance: ");
                }else {
                    for (int i = 0; i < attendanceResponse.getAttendanceList().size(); i++) {
//
//            calendar.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH),
//                    Integer.parseInt(attendanceResponse.getAttendanceList().get(i).date));
                        Attendance attendance = attendanceResponse.getAttendanceList().get(i);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = simpleDateFormat.parse(attendance.date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
//                status 0 absent 1 is present
                        if (attendance.getStatus().equals("0")) {
                            EventDay eventDay = new EventDay(calendar, R.drawable.absent_selected_day);
                            eventDays.add(eventDay);
                        } else {
                            EventDay eventDay = new EventDay(calendar, R.drawable.present_selected_day);
                            eventDays.add(eventDay);
                        }
                    }
                }

                if (attendanceResponse.getHolidayDateList() == null) {
                    Log.d(TAG, "onSuccessAttendance: ");
                }else {
                    for (int i = 0; i < attendanceResponse.getHolidayDateList().size(); i++) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH),
//                    Integer.parseInt(attendanceResponse.getHolidayDateList().get(i).date));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = simpleDateFormat.parse(attendanceResponse.getHolidayDateList().get(i).date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        EventDay eventDay = new EventDay(calendar, R.drawable.holiday_selected_day);
                        eventDays.add(eventDay);
                    }
                }

                Log.d(TAG, "eventDays " + eventDays.size());
                binding.calendarView.setEvents(eventDays);
            } catch (Exception e) {
                e.printStackTrace();
            }



        }
            else {

                Snackbar.make(findViewById(android.R.id.content), attendanceResponse.getDescription(), Snackbar.LENGTH_SHORT).show();
            }



        getLastThreeAttendanceReport();
    }


    private class ValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public ValueFormatter() {
            mFormat = new DecimalFormat("######.0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }

    private class Data {

        public String xAxisValue;
        public float yValue;
        public float xValue;

        public Data(float xValue, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }

    private void setYearList() {
        for (int i = currentCalendar.get(Calendar.YEAR); i > currentCalendar.get(Calendar.YEAR) - 2; i--) {
            yearsList.add(i);
        }
        Collections.reverse(yearsList);
    }


    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
