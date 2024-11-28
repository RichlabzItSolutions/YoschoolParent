package com.credoapp.parent.model.monthlySllabusModels;

import com.google.gson.annotations.SerializedName;

public class GetMonthlySyllabusListResults {


    @SerializedName("syllabus_id")
    private String syllabusId;
    @SerializedName("class_name")
    private String className;
    @SerializedName("admin_id")
    private String adminId;
    @SerializedName("teacher_name")
    private String teacherName;
    @SerializedName("syllabus_description")
    private String syllabusDescription;
    @SerializedName("subject")
    private String subject;
    @SerializedName("url")
    private String imageUrl;


    public String getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(String syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSyllabusDescription() {
        return syllabusDescription;
    }

    public void setSyllabusDescription(String syllabusDescription) {
        this.syllabusDescription = syllabusDescription;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
