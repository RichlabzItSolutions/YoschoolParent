package com.credoapp.parent.model;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class EventAPIResponse extends APIResponse {

    List<Events> eventsList;


    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    @Override
    public String toString() {
        return "EventAPIResponse{" +
                "eventsList=" + eventsList +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
