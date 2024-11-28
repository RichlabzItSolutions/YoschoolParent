package com.credoapp.parent.model.vaccineModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VaccineModelResponse {


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

    public List<VaccineModelResults> getResults() {
        return results;
    }

    public void setResults(List<VaccineModelResults> results) {
        this.results = results;
    }

    @SerializedName("code")
    private String responseCode;
    private String description;
    @SerializedName("result")
    private List<VaccineModelResults> results;

}
