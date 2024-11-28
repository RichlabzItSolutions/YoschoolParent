package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Assignment {

    //    {"code":200,"message":
    // "Success","result":[{"title":"Solve the addition problem","from_date":"2018-06-26",
// "to_date":"2018-06-27","subject_name":"Maths","teacher_name":"seshu"}]}
    public String title;
    public String from_date;
    public String to_date;
    public String subject_name;
    public String teacher_name;
    @SerializedName("assignment_image")
    public String assignmentImage;
    private String assignmentId;

    public String getAssignment_type() {
        return assignment_type;
    }

    public void setAssignment_type(String assignment_type) {
        this.assignment_type = assignment_type;
    }

    public String assignment_type;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }


    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", from_date='" + from_date + '\'' +
                ", to_date='" + to_date + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                '}';
    }

    public String getAssignmentImage() {
        return assignmentImage;
    }

    public void setAssignmentImage(String assignmentImage) {
        this.assignmentImage = assignmentImage;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }
}
