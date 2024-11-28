package com.credoapp.parent.model.donateBooks;

import com.google.gson.annotations.SerializedName;

public class DonateBooksGetRequiredRequest {

    @SerializedName("student_id")
    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


}
