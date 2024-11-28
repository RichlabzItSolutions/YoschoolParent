package com.credoapp.parent.model.attendanceModels;

public class ThreeMonthsDataResult {

    public String month;
    public String totalDays;
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

}
