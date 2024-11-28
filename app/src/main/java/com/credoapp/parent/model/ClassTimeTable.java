package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ClassTimeTable {

    @SerializedName("Monday")
    List<DayTimeTable> mondayList;

    @SerializedName("Tuesday")
    List<DayTimeTable> tuesdayList;

    @SerializedName("Wednesday")
    List<DayTimeTable> wednesdayList;

    @SerializedName("Thursday")
    List<DayTimeTable> thursdayList;

    @SerializedName("Friday")
    List<DayTimeTable> fridayList;

    @SerializedName("Saturday")
    List<DayTimeTable> saturdayList;


    public List<DayTimeTable> getMondayList() {
        return mondayList;
    }

    public void setMondayList(List<DayTimeTable> mondayList) {
        this.mondayList = mondayList;
    }

    public List<DayTimeTable> getTuesdayList() {
        return tuesdayList;
    }

    public void setTuesdayList(List<DayTimeTable> tuesdayList) {
        this.tuesdayList = tuesdayList;
    }

    public List<DayTimeTable> getWednesdayList() {
        return wednesdayList;
    }

    public void setWednesdayList(List<DayTimeTable> wednesdayList) {
        this.wednesdayList = wednesdayList;
    }

    public List<DayTimeTable> getThursdayList() {
        return thursdayList;
    }

    public void setThursdayList(List<DayTimeTable> thursdayList) {
        this.thursdayList = thursdayList;
    }

    public List<DayTimeTable> getFridayList() {
        return fridayList;
    }

    public void setFridayList(List<DayTimeTable> fridayList) {
        this.fridayList = fridayList;
    }

    public List<DayTimeTable> getSaturdayList() {
        return saturdayList;
    }

    public void setSaturdayList(List<DayTimeTable> saturdayList) {
        this.saturdayList = saturdayList;
    }


    @Override
    public String toString() {
        return "ClassTimeTable{" +
                "mondayList=" + mondayList +
                ", tuesdayList=" + tuesdayList +
                ", wednesdayList=" + wednesdayList +
                ", thursdayList=" + thursdayList +
                ", fridayList=" + fridayList +
                ", saturdayList=" + saturdayList +
                '}';
    }
}
