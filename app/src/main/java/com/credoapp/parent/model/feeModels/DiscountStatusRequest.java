package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

public class DiscountStatusRequest {

    @SerializedName("admin_id")
    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
