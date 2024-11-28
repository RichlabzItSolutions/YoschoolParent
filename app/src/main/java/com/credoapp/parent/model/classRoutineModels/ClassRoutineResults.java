package com.credoapp.parent.model.classRoutineModels;

import com.google.gson.annotations.SerializedName;

public class ClassRoutineResults {

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


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
