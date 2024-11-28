package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class TimeTable {
    public String exam_category_id;
    public String exam_category;
    @SerializedName("class")
    public String class_name;
    @SerializedName("subject_name")
    public String subject;
    public String start_date;
    public String end_date;
    private String status;
    @SerializedName("exam_start_time")
    private String fromTime;
    @SerializedName("exam_end_time")
    private String toTime;
    @SerializedName("marks")
    private String totalMarks;
    @SerializedName("obtained_marks")
    private String gainedMarks;

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

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getGainedMarks() {
        return gainedMarks;
    }

    public void setGainedMarks(String gainedMarks) {
        this.gainedMarks = gainedMarks;
    }



    public String getExam_category_id() {
        return exam_category_id;
    }

    public void setExam_category_id(String exam_category_id) {
        this.exam_category_id = exam_category_id;
    }

    public String getExam_category() {
        return exam_category;
    }

    public void setExam_category(String exam_category) {
        this.exam_category = exam_category;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "exam_category_id='" + exam_category_id + '\'' +
                ", exam_category='" + exam_category + '\'' +
                ", class_name='" + class_name + '\'' +
                ", subject='" + subject + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
