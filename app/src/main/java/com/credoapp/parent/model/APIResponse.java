package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class APIResponse {
    public int code;
    public String message;
    public String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
