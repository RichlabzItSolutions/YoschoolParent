package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

public class MyDonationsList {

    @SerializedName("student_id")
    private String studentId;
    private String description;
    private String donation_mode;
    private String student_name;
    private String amount;
    private String classes;
    private String syllabus;
    @SerializedName("student_phone")
    private String number;
    private String donation_id;
    private String title;
    private String photo;
    private String emptyTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDonation_mode() {
        return donation_mode;
    }

    public void setDonation_mode(String donation_mode) {
        this.donation_mode = donation_mode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(String donation_id) {
        this.donation_id = donation_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmptyTitle() {
        return emptyTitle;
    }

    public void setEmptyTitle(String emptyTitle) {
        this.emptyTitle = emptyTitle;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
