package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class Messages {
    public String sms_id;
    public String message;
    public String date;
    public String identity;

    public String getSms_id() {
        return sms_id;
    }

    public void setSms_id(String sms_id) {
        this.sms_id = sms_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    @Override
    public String toString() {
        return "Messages{" +
                "sms_id='" + sms_id + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
