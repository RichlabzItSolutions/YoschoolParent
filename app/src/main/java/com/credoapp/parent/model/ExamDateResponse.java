package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ExamDateResponse  {


     int code;
     String description;
    @SerializedName("result")
    ExamTimeTableResponse examTimeTableResponse;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExamTimeTableResponse getExamTimeTableResponse() {
        return examTimeTableResponse;
    }

    public void setExamTimeTableResponse(ExamTimeTableResponse examTimeTableResponse) {
        this.examTimeTableResponse = examTimeTableResponse;
    }

//    @Override
//    public String toString() {
//        return "ExamDateResponse{" +
//                "examTimeTableResponse=" + examTimeTableResponse +
//                ", code=" + code +
//                ", message='" + message + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }
}
