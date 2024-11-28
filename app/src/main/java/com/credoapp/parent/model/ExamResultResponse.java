package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ExamResultResponse {

    @SerializedName("exams")
    List<ExamResults> examList;


    public List<ExamResults> getExamList() {
        return examList;
    }

    public void setExamList(List<ExamResults> examList) {
        this.examList = examList;
    }

    @Override
    public String toString() {
        return "ExamTimeTableResponse{" +
                "examList=" + examList +
                '}';
    }
}
