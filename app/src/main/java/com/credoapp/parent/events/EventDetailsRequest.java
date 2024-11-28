package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

public class EventDetailsRequest {

    @SerializedName("event_id")
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}
