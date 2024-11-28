package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class Fee {
    public String total_amount;
    public String paid_amount;
    public String due_amount;
    public String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "total_amount='" + total_amount + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                ", due_amount='" + due_amount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
