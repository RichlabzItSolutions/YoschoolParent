package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

import java.security.PrivateKey;

public class EventsResultsList {

    @SerializedName("event_date")
    private String eventDate;
    @SerializedName("title")
    private String eventTitle;
    @SerializedName("photo")
    private String eventImageUrl;
    @SerializedName("event_id")
    private String eventId;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

}
