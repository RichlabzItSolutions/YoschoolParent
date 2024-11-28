package com.credoapp.parent.model.donateBooks;

import com.google.gson.annotations.SerializedName;

public class ClassList {


    @SerializedName("class_id")
    private String classId;

    @SerializedName("class_name")
    private String className;


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


}
