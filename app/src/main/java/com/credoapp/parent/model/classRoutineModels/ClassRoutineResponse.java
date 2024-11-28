package com.credoapp.parent.model.classRoutineModels;

import com.credoapp.parent.model.AttendanceReport;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassRoutineResponse {

    private int code;
    private String message;
    private String description;
    @SerializedName("Monday")
    private List<ClassRoutineResults> resultsMonday;
    @SerializedName("Tuesday")
    private List<ClassRoutineResults> resultsTuesday;
    @SerializedName("Wednesday")
    private List<ClassRoutineResults> resultsWednesday;
    @SerializedName("Thursday")
    private List<ClassRoutineResults> resultsThursday;
    @SerializedName("Friday")
    private List<ClassRoutineResults> resultsFriday;
    @SerializedName("Saturday")
    private List<ClassRoutineResults> resultsSaturday;




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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






    public List<ClassRoutineResults> getResultsMonday() {
        return resultsMonday;
    }

    public void setResultsMonday(List<ClassRoutineResults> resultsMonday) {
        this.resultsMonday = resultsMonday;
    }

    public List<ClassRoutineResults> getResultsTuesday() {
        return resultsTuesday;
    }

    public void setResultsTuesday(List<ClassRoutineResults> resultsTuesday) {
        this.resultsTuesday = resultsTuesday;
    }

    public List<ClassRoutineResults> getResultsWednesday() {
        return resultsWednesday;
    }

    public void setResultsWednesday(List<ClassRoutineResults> resultsWednesday) {
        this.resultsWednesday = resultsWednesday;
    }

    public List<ClassRoutineResults> getResultsThursday() {
        return resultsThursday;
    }

    public void setResultsThursday(List<ClassRoutineResults> resultsThursday) {
        this.resultsThursday = resultsThursday;
    }

    public List<ClassRoutineResults> getResultsFriday() {
        return resultsFriday;
    }

    public void setResultsFriday(List<ClassRoutineResults> resultsFriday) {
        this.resultsFriday = resultsFriday;
    }

    public List<ClassRoutineResults> getResultsSaturday() {
        return resultsSaturday;
    }

    public void setResultsSaturday(List<ClassRoutineResults> resultsSaturday) {
        this.resultsSaturday = resultsSaturday;
    }



}
