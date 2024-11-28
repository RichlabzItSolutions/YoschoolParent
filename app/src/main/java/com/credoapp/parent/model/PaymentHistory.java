package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class PaymentHistory {

    public String fee_lable_title;
    public String term_title;
    public String receipt_id;
    public String paid_amount;
    public String paid_on;
    public String paid_status;
    public String cancel_status;
    public String fee_label_name;
    public String pay_mode;


    public String getFee_lable_title() {
        return fee_lable_title;
    }

    public void setFee_lable_title(String fee_lable_title) {
        this.fee_lable_title = fee_lable_title;
    }

    public String getTerm_title() {
        return term_title;
    }

    public void setTerm_title(String term_title) {
        this.term_title = term_title;
    }



    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getPaid_on() {
        return paid_on;
    }

    public void setPaid_on(String paid_on) {
        this.paid_on = paid_on;
    }

    public String getPaid_status() {
        return paid_status;
    }

    public void setPaid_status(String paid_status) {
        this.paid_status = paid_status;
    }

    public String getCancel_status() {
        return cancel_status;
    }

    public void setCancel_status(String cancel_status) {
        this.cancel_status = cancel_status;
    }

    public String getFee_label_name() {
        return fee_label_name;
    }

    public void setFee_label_name(String fee_label_name) {
        this.fee_label_name = fee_label_name;
    }

    public String getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(String pay_mode) {
        this.pay_mode = pay_mode;
    }


    @Override
    public String toString() {
        return "PaymentHistory{" +
                "receipt_id='" + receipt_id + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                ", paid_on='" + paid_on + '\'' +
                ", paid_status='" + paid_status + '\'' +
                ", cancel_status='" + cancel_status + '\'' +
                ", fee_label_name='" + fee_label_name + '\'' +
                ", pay_mode='" + pay_mode + '\'' +
                '}';
    }
}
