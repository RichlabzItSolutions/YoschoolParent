package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class DayTimeTable {

    public String subject;
    public String time;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ClassTimeTable{" +
                "subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
