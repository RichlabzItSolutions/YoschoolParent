package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonateListResponse {



    @SerializedName("code")
    private String responseCode;

    private String message;

    private String description;

    @SerializedName("otherDonations")
    private List<MyDonationsList> othersDonationsLists;

    @SerializedName("myDonations")
    private List<MyDonationsList> myDonationsLists;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<MyDonationsList> getOthersDonationsLists() {
        return othersDonationsLists;
    }

    public void setOthersDonationsLists(List<MyDonationsList> othersDonationsLists) {
        this.othersDonationsLists = othersDonationsLists;
    }

    public List<MyDonationsList> getMyDonationsLists() {
        return myDonationsLists;
    }

    public void setMyDonationsLists(List<MyDonationsList> myDonationsLists) {
        this.myDonationsLists = myDonationsLists;
    }



}
