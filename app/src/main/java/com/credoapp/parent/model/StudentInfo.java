package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class StudentInfo {

    public String student_id;
    public String student_name;
    public String student_photo;
    public String class_name;
    public String school_name;
    public String amount_per_student;
    public String parent_pay_mode;
    public String logo;
    public String class_id;
    public String payment_status;
    public boolean isChecked;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }



    public String getAmount_per_student() {
        return amount_per_student;
    }

    public void setAmount_per_student(String amount_per_student) {
        this.amount_per_student = amount_per_student;
    }

    public String getParent_pay_mode() {
        return parent_pay_mode;
    }

    public void setParent_pay_mode(String parent_pay_mode) {
        this.parent_pay_mode = parent_pay_mode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }




    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_photo() {
        return student_photo;
    }

    public void setStudent_photo(String student_photo) {
        this.student_photo = student_photo;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }



    @Override
    public String toString() {
        return "StudentInfo{" +
                "student_id='" + student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", student_photo='" + student_photo + '\'' +
                ", class_name='" + class_name + '\'' +
                ", school_name='" + school_name + '\'' +
                ", amount_per_student='" + amount_per_student + '\'' +
                ", parent_pay_mode='" + parent_pay_mode + '\'' +
                ", logo='" + logo + '\'' +
                ", payment_status='" + payment_status + '\'' +
                '}';
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
