package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ExamResultsResponse extends APIResponse {

    @SerializedName("result")
    ExamResultResponse examTimeTableResponse;


    public ExamResultResponse getExamTimeTableResponse() {
        return examTimeTableResponse;
    }

    public void setExamTimeTableResponse(ExamResultResponse examTimeTableResponse) {
        this.examTimeTableResponse = examTimeTableResponse;
    }

    @Override
    public String toString() {
        return "ExamResultsResponse{" +
                "examTimeTableResponse=" + examTimeTableResponse +
                '}';
    }
}
