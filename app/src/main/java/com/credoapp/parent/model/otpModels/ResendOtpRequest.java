package com.credoapp.parent.model.otpModels;

import com.google.gson.annotations.SerializedName;

public class ResendOtpRequest {

    @SerializedName("mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
