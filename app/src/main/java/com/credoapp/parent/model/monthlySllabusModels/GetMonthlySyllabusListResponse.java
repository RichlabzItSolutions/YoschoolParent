package com.credoapp.parent.model.monthlySllabusModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMonthlySyllabusListResponse {

    private String code;
    private String description;
    @SerializedName("monthly_syllabus_data")
    private List<GetMonthlySyllabusListResults> results;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<GetMonthlySyllabusListResults> getResults() {
        return results;
    }

    public void setResults(List<GetMonthlySyllabusListResults> results) {
        this.results = results;
    }
}
