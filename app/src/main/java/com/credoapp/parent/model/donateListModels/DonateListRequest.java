package com.credoapp.parent.model.donateListModels;

import com.google.gson.annotations.SerializedName;

public class DonateListRequest {
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("admin_id")
    private String adminId;


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }



    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

}
