package com.credoapp.parent.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.TimeTableAdapter;
import com.credoapp.parent.model.DayTimeTable;

import java.util.List;

public class TimeTableFragment  extends Fragment {
    RecyclerView day_recycler_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_time_table_layout, container, false);
        day_recycler_view = view.findViewById(R.id.day_recycler_view);
        return view;
    }

    public void updateMonTimeTableSheet(List<DayTimeTable> mondayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(day_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        day_recycler_view.addItemDecoration(dividerItemDecoration);
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), mondayList);
        day_recycler_view.setAdapter(timeTableAdapter);
    }

    public void updateTueTimeTableSheet(List<DayTimeTable> tuesdayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(day_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        day_recycler_view.addItemDecoration(dividerItemDecoration);
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), tuesdayList);
        day_recycler_view.setAdapter(timeTableAdapter);
    }

    public void updateWedTimeTableSheet(List<DayTimeTable> wednesdayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(day_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        day_recycler_view.addItemDecoration(dividerItemDecoration);
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), wednesdayList);
        day_recycler_view.setAdapter(timeTableAdapter);
    }

    public void updateThuTimeTableSheet(List<DayTimeTable> thursdayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(day_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        day_recycler_view.addItemDecoration(dividerItemDecoration);
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), thursdayList);
        day_recycler_view.setAdapter(timeTableAdapter);
    }

    public void updateFriTimeTableSheet(List<DayTimeTable> fridayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(day_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        day_recycler_view.addItemDecoration(dividerItemDecoration);
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), fridayList);
        day_recycler_view.setAdapter(timeTableAdapter);
    }

    public void updateSatTimeTableSheet(List<DayTimeTable> saturdayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        day_recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(day_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        day_recycler_view.addItemDecoration(dividerItemDecoration);
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), saturdayList);
        day_recycler_view.setAdapter(timeTableAdapter);
    }
}
