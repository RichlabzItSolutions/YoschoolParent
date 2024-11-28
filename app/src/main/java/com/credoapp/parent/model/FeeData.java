package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class FeeData {
    String fee_type;
    String paid_amount;

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    @Override
    public String toString() {
        return "FeeData{" +
                "fee_type='" + fee_type + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                '}';
    }
}
