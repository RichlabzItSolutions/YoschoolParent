package com.credoapp.parent.model.vaccineModels;

import com.google.gson.annotations.SerializedName;

public class VaccineModelResults {

    public String getVaccineMonth() {
        return vaccineMonth;
    }

    public void setVaccineMonth(String vaccineMonth) {
        this.vaccineMonth = vaccineMonth;
    }

    public String getVaccineDescription() {
        return vaccineDescription;
    }

    public void setVaccineDescription(String vaccineDescription) {
        this.vaccineDescription = vaccineDescription;
    }

    @SerializedName("vaccine_month")
    private String vaccineMonth;
    @SerializedName("vaccine_description")
    private String vaccineDescription;
}
