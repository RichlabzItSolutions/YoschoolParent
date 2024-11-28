package com.credoapp.parent.model.timeTableModels;

import com.credoapp.parent.model.AttendanceReport;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableResponse {

    private int code;
    private String message;
    private String description;
    @SerializedName("time_table")
    private List<TimeTableResults> results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<TimeTableResults> getResults() {
        return results;
    }

    public void setResults(List<TimeTableResults> results) {
        this.results = results;
    }
}
