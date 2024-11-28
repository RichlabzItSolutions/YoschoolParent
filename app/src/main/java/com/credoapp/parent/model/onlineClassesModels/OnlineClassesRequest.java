package com.credoapp.parent.model.onlineClassesModels;

import com.google.gson.annotations.SerializedName;

public class OnlineClassesRequest {

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getClassesDate() {
        return classesDate;
    }

    public void setClassesDate(String classesDate) {
        this.classesDate = classesDate;
    }

    @SerializedName("class_id")
    private String classId;
    @SerializedName("admin_id")
    private String adminId;
    @SerializedName("classes_date")
    private String classesDate;
}
