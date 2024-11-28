package com.credoapp.parent.model.classRoutineModels;

import com.google.gson.annotations.SerializedName;

public class ClassRoutineRequest {
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @SerializedName("admin_id")
    private String adminId;
    @SerializedName("class_id")
    private String classId;
    @SerializedName("student_id")
    private String studentId;
}
