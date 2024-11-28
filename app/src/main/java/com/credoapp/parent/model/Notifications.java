package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class Notifications {

    public String title;
    public String description;
    public String date;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Notifications{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
