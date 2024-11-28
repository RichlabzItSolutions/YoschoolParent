package com.credoapp.parent.model.holidaysModels;

import com.credoapp.parent.model.pdfModels.PdfResults;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HolidaysListResponse {


    private String code;
    private String message;
    private String description;
    @SerializedName("results")
    private List<HolidaysListResults> results;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<HolidaysListResults> getResults() {
        return results;
    }

    public void setResults(List<HolidaysListResults> results) {
        this.results = results;
    }


}
