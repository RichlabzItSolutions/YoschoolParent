package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class NotificationResponse extends APIResponse {

    @SerializedName("result")
    List<Notifications> notifications;

    int num_rows;

    public int notification_count;

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

    public int getNotification_count() {
        return notification_count;
    }

    public void setNotification_count(int notification_count) {
        this.notification_count = notification_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "notifications=" + notifications +
                ", notification_count=" + notification_count +
                ", description='" + description + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
