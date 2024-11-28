package com.credoapp.parent.model.holidaysModels;

import com.google.gson.annotations.SerializedName;

public class HolidaysListRequest {


    @SerializedName("admin_id")
    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
