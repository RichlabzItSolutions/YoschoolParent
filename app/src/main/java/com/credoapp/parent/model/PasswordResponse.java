package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class PasswordResponse extends APIResponse {


    @SerializedName("result")
    ParentInfo parentInfo;

    public ParentInfo getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(ParentInfo parentInfo) {
        this.parentInfo = parentInfo;
    }

    @Override
    public String toString() {
        return "PasswordResponse{" +
                "parentInfo=" + parentInfo +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
