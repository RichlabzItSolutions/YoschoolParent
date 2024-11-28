package com.credoapp.parent.model.pdfModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PdfResponse {


    private String code;
    private String message;
    private String description;
    @SerializedName("parent_pdf_data")
    private List<PdfResults> results;

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


    public List<PdfResults> getResults() {
        return results;
    }

    public void setResults(List<PdfResults> results) {
        this.results = results;
    }
}
