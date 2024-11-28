package com.credoapp.parent.model.addAssignment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccademicyearResponse {
    private String code;
    private String message;
    private String description;
    private String default_academic_year;
    @SerializedName("data")
    private List<AccademicyearModel> data;

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

    public String getDefault_academic_year() {
        return default_academic_year;
    }

    public void setDefault_academic_year(String default_academic_year) {
        this.default_academic_year = default_academic_year;
    }

    public List<AccademicyearModel> getData() {
        return data;
    }

    public void setData(List<AccademicyearModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccademicyearResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", default_academic_year='" + default_academic_year + '\'' +
                ", data=" + data +
                '}';
    }
}
