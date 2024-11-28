package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class LoginRequest {

    public String mobile;

    public String password;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
