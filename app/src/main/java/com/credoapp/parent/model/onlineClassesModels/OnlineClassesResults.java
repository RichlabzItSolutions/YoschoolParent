package com.credoapp.parent.model.onlineClassesModels;

import com.google.gson.annotations.SerializedName;

public class OnlineClassesResults {

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getJoinLink() {
        return joinLink;
    }

    public void setJoinLink(String joinLink) {
        this.joinLink = joinLink;
    }

    public String getStartLink() {
        return startLink;
    }

    public void setStartLink(String startLink) {
        this.startLink = startLink;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String topic;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("start_time")
    private String startTime;
    private String duration;
    @SerializedName("join_link")
    private String joinLink;
    @SerializedName("start_link")
    private String startLink;
    @SerializedName("meeting_id")
    private String meetingId;
    @SerializedName("added_on")
    private String addedOn;
    @SerializedName("class_name")
    private String className;
    @SerializedName("subject_name")
    private String subjectName;
    @SerializedName("class_status")
    private String classStatus;
    @SerializedName("id")
    private String id;



    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }
}
