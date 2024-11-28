package com.credoapp.parent.model.addAssignment;

public class AccademicyearModel {
    private String id;
    private String academic_year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    @Override
    public String toString() {
        return "AccademicyearModel{" +
                "id='" + id + '\'' +
                ", academic_year='" + academic_year + '\'' +
                '}';
    }
}
