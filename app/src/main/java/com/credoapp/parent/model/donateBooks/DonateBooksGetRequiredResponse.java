package com.credoapp.parent.model.donateBooks;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonateBooksGetRequiredResponse {


    @SerializedName("code")
    private String responseCode;

    private String message;

    private String description;

    @SerializedName("syllabus")
    private
    List<SyllabusList> syllabusLists;

    @SerializedName("classes")
    private
    List<ClassList> classList;

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

    public List<SyllabusList> getSyllabusLists() {
        return syllabusLists;
    }

    public void setSyllabusLists(List<SyllabusList> syllabusLists) {
        this.syllabusLists = syllabusLists;
    }

    public List<ClassList> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassList> classList) {
        this.classList = classList;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
