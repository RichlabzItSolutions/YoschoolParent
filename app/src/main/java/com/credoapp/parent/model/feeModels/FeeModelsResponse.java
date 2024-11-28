package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeeModelsResponse {

    private String code;
    private String message;
    private String description;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    @SerializedName("total_amount")
    private String totalAmount;
    @SerializedName("total_paid")
    private String totalPaid;
    @SerializedName("total_balance")
    private String totalBalance;
    @SerializedName("total_discount")
    private String totalDiscount;
    @SerializedName("result")
    private FeeModelsResults feeModelsResults;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public FeeModelsResults getFeeModelsResults() {
        return feeModelsResults;
    }

    public void setFeeModelsResults(FeeModelsResults feeModelsResults) {
        this.feeModelsResults = feeModelsResults;
    }
}
