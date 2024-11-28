package com.credoapp.parent.model.pdfModels;

import com.google.gson.annotations.SerializedName;

public class PdfRequest {

    @SerializedName("parent_id")
    private String userId;
    @SerializedName("student_id")
    private String studentId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
