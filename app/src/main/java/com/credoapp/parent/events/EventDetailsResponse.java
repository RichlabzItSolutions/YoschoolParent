package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetailsResponse {

    @SerializedName("code")
    private String responseCode;
    private String description;
    private String message;
    @SerializedName("photos")
    private List<EventDetailsPhotos> photos;
    @SerializedName("videos")
    private List<EventDetailsVideos> videos;
    @SerializedName("eventDetails")
    private EventDetailsResults results;


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

    public List<EventDetailsPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<EventDetailsPhotos> photos) {
        this.photos = photos;
    }

    public List<EventDetailsVideos> getVideos() {
        return videos;
    }

    public void setVideos(List<EventDetailsVideos> videos) {
        this.videos = videos;
    }

    public EventDetailsResults getResults() {
        return results;
    }

    public void setResults(EventDetailsResults results) {
        this.results = results;
    }


    public class EventDetailsResults {


        @SerializedName("event_id")
        private String eventId;
        private String title;
        private String description;
        @SerializedName("event_date")
        private String eventDate;
        @SerializedName("photo")
        private String photoUrl;


        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

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

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }


    }
}
