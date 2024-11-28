package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ClassTimeTableResponse extends APIResponse {

    @SerializedName("result")
    List<ClassTimeTable> classTimeTables;


    public List<ClassTimeTable> getClassTimeTables() {
        return classTimeTables;
    }

    public void setClassTimeTables(List<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
    }


    @Override
    public String toString() {
        return "ClassTimeTableResponse{" +
                "classTimeTables=" + classTimeTables +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
