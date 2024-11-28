package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

public class DeleteResponse {

    @SerializedName("code")
    private String responseCode;
    private String description;
    private String message;


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

}
