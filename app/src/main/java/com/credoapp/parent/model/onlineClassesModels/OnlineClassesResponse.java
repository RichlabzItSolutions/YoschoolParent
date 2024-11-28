package com.credoapp.parent.model.onlineClassesModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineClassesResponse {
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

    public List<OnlineClassesResults> getOnlineClassesResults() {
        return onlineClassesResults;
    }

    public void setOnlineClassesResults(List<OnlineClassesResults> onlineClassesResults) {
        this.onlineClassesResults = onlineClassesResults;
    }

    @SerializedName("code")
    private String responseCode;
    private String description;
    @SerializedName("data")
    public List<OnlineClassesResults> onlineClassesResults;

}
