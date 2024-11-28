package com.credoapp.parent.model.donateBooks;

import com.google.gson.annotations.SerializedName;

public class UpdateDonateBooksRequest {

    @SerializedName("student_id")
    private String studentId;
    @SerializedName("syllabus_id")
    private String syllabusId;
    @SerializedName("class_id")
    private String classId;
    @SerializedName("description")
    private String description;
    @SerializedName("donation_mode")
    private String donateMode;
    @SerializedName("amount")
    private String amount;
    @SerializedName("admin_id")
    private String adminId;

    @SerializedName("donation_id")
    private String donationId;
    private String picture;
    @SerializedName("picture_data")
    private String pictureData;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(String syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDonateMode() {
        return donateMode;
    }

    public void setDonateMode(String donateMode) {
        this.donateMode = donateMode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureData() {
        return pictureData;
    }

    public void setPictureData(String pictureData) {
        this.pictureData = pictureData;
    }
}
