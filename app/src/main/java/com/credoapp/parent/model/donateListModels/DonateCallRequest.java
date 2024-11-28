package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

public class DonateCallRequest {


    @SerializedName("donation_id")
    private String donationId;
    @SerializedName("student_id")
    private String studentId;


    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
