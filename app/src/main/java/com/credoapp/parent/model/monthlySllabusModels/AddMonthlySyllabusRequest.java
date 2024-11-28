package com.credoapp.parent.model.monthlySllabusModels;

import com.google.gson.annotations.SerializedName;

public class AddMonthlySyllabusRequest {

    private String  subject;
    @SerializedName("admin_id")
    private String adminId;
    @SerializedName("class_id")
    private String classId;
    @SerializedName("teacher_id")
    private String teacherId;
    @SerializedName("syllabus_description")
    private String syllabusDescription;
    private String month;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSyllabusDescription() {
        return syllabusDescription;
    }

    public void setSyllabusDescription(String syllabusDescription) {
        this.syllabusDescription = syllabusDescription;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
