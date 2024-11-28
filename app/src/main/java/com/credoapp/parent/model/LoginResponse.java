package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class LoginResponse extends APIResponse {
    @SerializedName("result")
    ParentInfo parentInfos;

    int exits;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParentInfo getParentInfos() {
        return parentInfos;
    }

    public void setParentInfos(ParentInfo parentInfos) {
        this.parentInfos = parentInfos;
    }


    public int getExits() {
        return exits;
    }

    public void setExits(int exits) {
        this.exits = exits;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "parentInfos=" + parentInfos +
                ", exits=" + exits +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

