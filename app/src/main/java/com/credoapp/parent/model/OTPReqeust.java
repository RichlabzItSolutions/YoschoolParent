package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class OTPReqeust {
    public String mobile;
    public String otp;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "OTPReqeust{" +
                "mobile='" + mobile + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
