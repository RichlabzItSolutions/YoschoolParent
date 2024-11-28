package com.credoapp.parent.model.attendanceModels;

import com.credoapp.parent.model.AttendanceReport;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreeMonthsDataResponse {

    private int code;
    private String message;
    private String description;
    @SerializedName("attendanceReport")
    private List<AttendanceReport> attendanceReports;

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

    public List<AttendanceReport> getAttendanceReports() {
        return attendanceReports;
    }

    public void setAttendanceReports(List<AttendanceReport> attendanceReports) {
        this.attendanceReports = attendanceReports;
    }




}
