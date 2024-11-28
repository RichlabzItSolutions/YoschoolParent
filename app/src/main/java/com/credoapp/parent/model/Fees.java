package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Fees {

    public int fee_type;
    public String fee_label_name;
    public String student_id;
    public String total_amount;
    public String paid_amount;
    public String balance;

    public String discount;

    public int getFee_type() {
        return fee_type;
    }

    public void setFee_type(int fee_type) {
        this.fee_type = fee_type;
    }

    public String getFee_label_name() {
        return fee_label_name;
    }

    public void setFee_label_name(String fee_label_name) {
        this.fee_label_name = fee_label_name;
    }

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Fees{" +
                "fee_type=" + fee_type +
                ", fee_label_name='" + fee_label_name + '\'' +
                ", student_id='" + student_id + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                ", balance='" + balance + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }
}
