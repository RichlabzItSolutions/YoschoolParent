package com.credoapp.parent.model.monthlySllabusModels;

import com.google.gson.annotations.SerializedName;

public class GetMonthlySyllabusListRequest {

    @SerializedName("admin_id")
    private String adminId;
    private String month;
    @SerializedName("teacher_id")
    private String teacherId;
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("class_id")
    private String classId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
