package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class AttendanceReport {

    public String month;
    public String totalDays;

    public String getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(String absentDays) {
        this.absentDays = absentDays;
    }

    public String absentDays;
    public String presentDays;
    public double percentage;


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(String presentDays) {
        this.presentDays = presentDays;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "AttendanceReport{" +
                "month='" + month + '\'' +
                ", totalDays='" + totalDays + '\'' +
                ", presentDays='" + presentDays + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
