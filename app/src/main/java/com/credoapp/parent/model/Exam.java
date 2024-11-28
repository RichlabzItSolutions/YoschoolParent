package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Exam {

    public String exam_category_id;
    public String exam_category;
    public String status;

    public boolean isViewing() {
        return viewing;
    }

    public void setViewing(boolean viewing) {
        this.viewing = viewing;
    }

    public boolean viewing;
    @SerializedName("timeTables")
    List<TimeTable> timeTables;


    public String getExam_category_id() {
        return exam_category_id;
    }

    public void setExam_category_id(String exam_category_id) {
        this.exam_category_id = exam_category_id;
    }

    public String getExam_category() {
        return exam_category;
    }

    public void setExam_category(String exam_category) {
        this.exam_category = exam_category;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "exam_category_id='" + exam_category_id + '\'' +
                ", exam_category='" + exam_category + '\'' +
                ", status='" + status + '\'' +
                ", timeTables=" + timeTables +
                '}';
    }
}
