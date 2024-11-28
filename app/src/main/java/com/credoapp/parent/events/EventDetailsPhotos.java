package com.credoapp.parent.events;

import com.google.gson.annotations.SerializedName;

public class EventDetailsPhotos {

    private String photo;

    @SerializedName("photo_id")
    private String photoId;


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

}
