package com.credoapp.parent.model.donateBooks;

import com.google.gson.annotations.SerializedName;

public class SyllabusList {

    @SerializedName("syllabus_id")
    private String syllabusId;

    @SerializedName("syllabus_name")
    private String syllabusName;


    public String getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(String syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getSyllabusName() {
        return syllabusName;
    }

    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

}
