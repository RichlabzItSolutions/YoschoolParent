package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ExamTimeTableResponse {

    @SerializedName("exams")
    List<Exam> examList;


    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    @Override
    public String toString() {
        return "ExamTimeTableResponse{" +
                "examList=" + examList +
                '}';
    }
}
