package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

public class DiscountStatusResponse {


    @SerializedName("code")
    private String responseCode;
    private String message;
    private String description;
    @SerializedName("discount_status")
    private String status;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
