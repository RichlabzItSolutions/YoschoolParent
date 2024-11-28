package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ParentStudentResponse extends APIResponse {


    @SerializedName("parent_details")
    ParentDetails parentDetails;

    @SerializedName("student_details")
    StudentDetails studentInfo;


    public ParentDetails getParentDetails() {
        return parentDetails;
    }

    public void setParentDetails(ParentDetails parentDetails) {
        this.parentDetails = parentDetails;
    }

    public StudentDetails getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentDetails studentInfo) {
        this.studentInfo = studentInfo;
    }


    @Override
    public String toString() {
        return "ParentStudentResponse{" +
                "parentDetails=" + parentDetails +
                ", studentInfo=" + studentInfo +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
