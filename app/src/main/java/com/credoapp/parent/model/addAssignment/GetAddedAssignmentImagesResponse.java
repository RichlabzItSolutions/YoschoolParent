package com.credoapp.parent.model.addAssignment;

import com.credoapp.parent.model.holidaysModels.HolidaysListResults;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddedAssignmentImagesResponse {

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

    public List<AddAssignmentModel> getResults() {
        return results;
    }

    public void setResults(List<AddAssignmentModel> results) {
        this.results = results;
    }

    private String code;
    private String message;
    private String description;
    @SerializedName("result")
    private List<AddAssignmentModel> results;
}
