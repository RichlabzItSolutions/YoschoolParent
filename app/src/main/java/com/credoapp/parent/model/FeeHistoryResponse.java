package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class FeeHistoryResponse extends APIResponse {

    //paymentDetails
    @SerializedName("students_fee_details")
    List<PaymentHistory> paymentHistories;


    public List<PaymentHistory> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<PaymentHistory> paymentHistories) {
        this.paymentHistories = paymentHistories;
    }


    @Override
    public String toString() {
        return "FeeHistoryResponse{" +
                "paymentHistories=" + paymentHistories +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
