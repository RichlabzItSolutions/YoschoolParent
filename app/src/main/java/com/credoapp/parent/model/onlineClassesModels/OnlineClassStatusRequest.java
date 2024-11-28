package com.credoapp.parent.model.onlineClassesModels;

import com.google.gson.annotations.SerializedName;

public class OnlineClassStatusRequest {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    private String id;
    @SerializedName("class_status")
    private String classStatus;
    private String studentId;



}
