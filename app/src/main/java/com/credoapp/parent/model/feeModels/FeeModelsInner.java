package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

public class FeeModelsInner {


    public String getFeeLabelName() {
        return feeLabelName;
    }

    public void setFeeLabelName(String feeLabelName) {
        this.feeLabelName = feeLabelName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    @SerializedName("fee_lable_title")
    private String feeLabelName;
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("amount")
    private String totalAmount;
    @SerializedName("paid")
    private String paidAmount;
    private String balance;


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
