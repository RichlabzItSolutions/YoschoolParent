package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class Events {

    String event_id;
    String title;
    String event_date;
    String photo;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Events{" +
                "event_id='" + event_id + '\'' +
                ", title='" + title + '\'' +
                ", event_date='" + event_date + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
