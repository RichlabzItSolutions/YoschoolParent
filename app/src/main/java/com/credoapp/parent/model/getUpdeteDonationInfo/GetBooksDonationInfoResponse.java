package com.credoapp.parent.model.getUpdeteDonationInfo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class GetBooksDonationInfoResponse {


    private String code;
    private String description;
    @SerializedName("donation")
    private DonationBookList donationBookList;
    @SerializedName("class_id")
    private ArrayList<String> classIds;
    private String message;


    public ArrayList<String> getClassIds() {
        return classIds;
    }

    public void setClassIds(ArrayList<String> classIds) {
        this.classIds = classIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DonationBookList getDonationBookList() {
        return donationBookList;
    }

    public void setDonationBookList(DonationBookList donationBookList) {
        this.donationBookList = donationBookList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class DonationBookList {

        private String syllabus_id;
        private String description;
        private String donation_mode;
        private String amount;

        public String getSyllabus_id() {
            return syllabus_id;
        }

        public void setSyllabus_id(String syllabus_id) {
            this.syllabus_id = syllabus_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDonation_mode() {
            return donation_mode;
        }

        public void setDonation_mode(String donation_mode) {
            this.donation_mode = donation_mode;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }



    }


}
