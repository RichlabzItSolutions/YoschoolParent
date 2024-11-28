package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ExamResult {
    public String exam_category_id;
    public String exam_category;
    @SerializedName("class")
    public String class_name;
    public String subject;
    public String status;
    public String comment;
    public String marks;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
                "exam_category_id='" + exam_category_id + '\'' +
                ", exam_category='" + exam_category + '\'' +
                ", class_name='" + class_name + '\'' +
                ", subject='" + subject + '\'' +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", marks='" + marks + '\'' +
                '}';
    }
}
