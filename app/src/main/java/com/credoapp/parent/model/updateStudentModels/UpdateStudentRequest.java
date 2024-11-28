package com.credoapp.parent.model.updateStudentModels;

import com.google.gson.annotations.SerializedName;

public class UpdateStudentRequest {



    @SerializedName("student_id")
    private String studentId;
    @SerializedName("student_name")
    private String studentName;
    @SerializedName("mother_name")
    private String motherName;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("address")
    private String address;


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
