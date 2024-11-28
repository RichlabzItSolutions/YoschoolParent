package com.credoapp.parent.model.otpModelsForNumber;

import com.google.gson.annotations.SerializedName;

public class OtpRequest {

    public String getChangeMobileTo() {
        return changeMobileTo;
    }

    public void setChangeMobileTo(String changeMobileTo) {
        this.changeMobileTo = changeMobileTo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @SerializedName("change_mobile_to")
    private String changeMobileTo;
    private String otp;
}
