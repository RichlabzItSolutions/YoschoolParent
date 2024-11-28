package com.credoapp.parent.model.holidaysModels;

import com.google.gson.annotations.SerializedName;

public class HolidaysListResults {

    @SerializedName("holiday_date")
    private String dateOfHoliday;
    @SerializedName("holiday_description")
    private String descriptionOfHoliday;
    @SerializedName("from_date")
    private String fromDate;
    @SerializedName("to_date")
    private String toDate;

    public String getDateOfHoliday() {
        return dateOfHoliday;
    }

    public void setDateOfHoliday(String dateOfHoliday) {
        this.dateOfHoliday = dateOfHoliday;
    }

    public String getDescriptionOfHoliday() {
        return descriptionOfHoliday;
    }

    public void setDescriptionOfHoliday(String descriptionOfHoliday) {
        this.descriptionOfHoliday = descriptionOfHoliday;
    }


    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
