package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class AssignmentResponse extends APIResponse {

    @SerializedName("result")
    public List<Assignment> assignments;


    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "AssignmentResponse{" +
                "assignments=" + assignments +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
