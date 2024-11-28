package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

public class OthersDonationsList {

    @SerializedName("student_id")
    private String studentId;
    private String name;
    private String description;
    private String donation_mode;
    private String amount;
    private String classes;
    private String syllabus;
    private String number;
    private String title;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
