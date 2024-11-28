package com.credoapp.parent.model.timeTableModels;

import com.google.gson.annotations.SerializedName;

public class TimeTableResults {

    private String day;
    @SerializedName("subject_name")
    private String subject;
    @SerializedName("from_time")
    private String fromTime;
    @SerializedName("to_time")
    private String toTime;


    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



}
