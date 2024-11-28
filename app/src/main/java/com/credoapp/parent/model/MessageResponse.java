package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class MessageResponse extends APIResponse {

    @SerializedName("result")
    public List<Messages> messages;

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }


    @Override
    public String toString() {
        return "MessageResponse{" +
                "messages=" + messages +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
