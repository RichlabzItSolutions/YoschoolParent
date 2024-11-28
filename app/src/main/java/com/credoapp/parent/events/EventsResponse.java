package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventsResponse {

    @SerializedName("code")
    private String responseCode;
    private String description;
    private String message;
    @SerializedName("events")
    private List<EventsResultsList> results;

    public List<EventsResultsList> getResults() {
        return results;
    }

    public void setResults(List<EventsResultsList> results) {
        this.results = results;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
