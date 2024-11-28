package com.credoapp.parent.model.examsModels;

import com.google.gson.annotations.SerializedName;

public class ExamDateRequest {
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("class_id")
    private String classId;
    @SerializedName("academic_year")
    private String accademicYear;
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getAccademicYear() {
        return accademicYear;
    }

    public void setAccademicYear(String accademicYear) {
        this.accademicYear = accademicYear;
    }
}
