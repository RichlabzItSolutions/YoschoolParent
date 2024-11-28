package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class FeesDetails {

    public String student_id;
    public String total_amount;
    public String paid_amount;
    public String due_amount;


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    @Override
    public String toString() {
        return "FeesDetails{" +
                "student_id='" + student_id + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                ", due_amount='" + due_amount + '\'' +
                '}';
    }
}
