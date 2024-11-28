package com.credoapp.parent.model.paymentModels;

import java.util.ArrayList;

public class PaymentRequestIds {


    public ArrayList getStudents() {
        return students;
    }

    public void setStudents(ArrayList students) {
        this.students = students;
    }

    private ArrayList students;
    private String tnxId;

    public String getTnxId() {
        return tnxId;
    }

    public void setTnxId(String tnxId) {
        this.tnxId = tnxId;
    }
}
