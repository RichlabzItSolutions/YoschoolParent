package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

public class GlobalResponse {

    @SerializedName("code")
    private String responseCode;
    private String description;
    private String message;
    @SerializedName("trip_status")
    private String tripStatus;
    @SerializedName("drop_status")
    private String dropStatus;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getDropStatus() {
        return dropStatus;
    }

    public void setDropStatus(String dropStatus) {
        this.dropStatus = dropStatus;
    }
}
