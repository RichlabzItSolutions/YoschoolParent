package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

public class EventsRequest {

    @SerializedName("admin_id")
    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

}
