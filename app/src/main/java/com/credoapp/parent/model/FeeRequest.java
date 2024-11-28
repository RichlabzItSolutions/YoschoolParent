package com.credoapp.parent.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class FeeRequest {
    //    {"student_id":466,"feeData":
// [{"fee_type":1,"paid_amount":1000},{"fee_type":2,"paid_amount":500},{"fee_type":3,"paid_amount":0}]}
    String student_id;
    @SerializedName("feeData")
    List<FeeData> feeDataList;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public List<FeeData> getFeeDataList() {
        return feeDataList;
    }

    public void setFeeDataList(List<FeeData> feeDataList) {
        this.feeDataList = feeDataList;
    }

    @Override
    public String toString() {
        return "FeeRequest{" +
                "student_id='" + student_id + '\'' +
                ", feeDataList=" + feeDataList +
                '}';
    }
}
