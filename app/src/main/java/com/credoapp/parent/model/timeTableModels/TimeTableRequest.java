package com.credoapp.parent.model.timeTableModels;

import com.google.gson.annotations.SerializedName;

public class TimeTableRequest {
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("admin_id")
    private String adminId;
    @SerializedName("class_id")
    private String classId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
