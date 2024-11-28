package com.credoapp.parent.model.UpdateParentModel;

import com.google.gson.annotations.SerializedName;

public class UpdateParentRequest {


    @SerializedName("parent_id")
    private String parentId;
    @SerializedName("parent_name")
    private String parentName;
    @SerializedName("mobile")
    private String parentMobile;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

}
