package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class ParentInfo {
    //    "result":{"parent_id":"112","parent_name":"seshu","otp_status":"1","admin_id":"62"}}
    String parent_id;
    String parent_name;
    String otp_status;
    String parent_phone;
    String admin_id;

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_status() {
        return otp_status;
    }

    public void setParent_status(String parent_status) {
        this.otp_status = parent_status;
    }


    public String getOtp_status() {
        return otp_status;
    }

    public void setOtp_status(String otp_status) {
        this.otp_status = otp_status;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public String toString() {
        return "ParentInfo{" +
                "parent_id='" + parent_id + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", otp_status='" + otp_status + '\'' +
                ", parent_phone='" + parent_phone + '\'' +
                ", admin_id='" + admin_id + '\'' +
                '}';
    }
}
