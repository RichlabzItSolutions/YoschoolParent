package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceReportResponse extends APIResponse {

    @SerializedName("attendanceReport")
    List<AttendanceReport> attendanceReports;


    public List<AttendanceReport> getAttendanceReports() {
        return attendanceReports;
    }

    public void setAttendanceReports(List<AttendanceReport> attendanceReports) {
        this.attendanceReports = attendanceReports;
    }


    @Override
    public String toString() {
        return "AttendanceReportResponse{" +
                "attendanceReports=" + attendanceReports +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
