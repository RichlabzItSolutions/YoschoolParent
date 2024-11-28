package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

public class FeeModelsRequest {

    @SerializedName("student_id")
    private String studentId;
    @SerializedName("academic_year")
    private String academicYear;

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }




}
