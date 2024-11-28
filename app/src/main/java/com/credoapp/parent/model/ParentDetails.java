package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class ParentDetails {
    public String parent_id;
    public String parent_name;
    public String parent_mobile;
    public String relation_with_student;

    public String getParent_id() {
        return parent_id;
    }


    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_mobile() {
        return parent_mobile;
    }

    public void setParent_mobile(String parent_mobile) {
        this.parent_mobile = parent_mobile;
    }

    public String getRelation_with_student() {
        return relation_with_student;
    }

    public void setRelation_with_student(String relation_with_student) {
        this.relation_with_student = relation_with_student;
    }
}
