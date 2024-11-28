package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

public class EventDetailsVideos {


    @SerializedName("youtube_url")
    private String youtubeUrl;

    @SerializedName("youtube_id")
    private String youtubeId;


    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

}
