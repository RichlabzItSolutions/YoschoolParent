package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

public class DeleteRequest {

    @SerializedName("donation_id")
    private String donationId;

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }



}
