package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class AttendanceResponse extends APIResponse {
//        {"code":200,"description":"2 records found",
// "attendance":[{"subject":"Maths","class":"LKG","teacher":"seshu","date":"2018-07-06","status":"1"},
// {"subject":"Maths","class":"LKG","teacher":"seshu","date":"2018-07-20","status":"1"}],
// "present":2,"absent":0,"holiday":0}
    @SerializedName("attendance")
    List<Attendance> attendanceList;

    @SerializedName("holidayDate")
    List<HolidayDate> holidayDateList;

    int holiday;
    int present;
    int absent;

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public int getHoliday() {
        return holiday;
    }

    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public List<HolidayDate> getHolidayDateList() {
        return holidayDateList;
    }

    public void setHolidayDateList(List<HolidayDate> holidayDateList) {
        this.holidayDateList = holidayDateList;
    }

    @Override
    public String toString() {
        return "AttendanceResponse{" +
                "attendanceList=" + attendanceList +
                ", holidayDateList=" + holidayDateList +
                ", holiday=" + holiday +
                ", present=" + present +
                ", absent=" + absent +
                '}';
    }
}
