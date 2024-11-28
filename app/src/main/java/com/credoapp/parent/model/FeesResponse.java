package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class FeesResponse extends APIResponse {

    @SerializedName("feeDetails")
    List<Fees> fees;

    public String total_amount;
    public String discount;
    public String paid_amount;
    public String balance;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "FeesResponse{" +
                "fees=" + fees +
                ", total_amount='" + total_amount + '\'' +
                ", discount='" + discount + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
