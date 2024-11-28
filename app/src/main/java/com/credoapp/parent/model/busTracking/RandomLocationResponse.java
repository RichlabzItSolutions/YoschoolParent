package com.credoapp.parent.model.busTracking;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RandomLocationResponse {


    private String code;
    @SerializedName("result")
    private ArrayList<LocationS> locationS;




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<LocationS> getLocationS() {
        return locationS;
    }

    public void setLocationS(ArrayList<LocationS> locationS) {
        this.locationS = locationS;
    }


    public class LocationS {

        @SerializedName("driver_id")
        private String driverId;
        private String latitude;
        private String longitude;
        @SerializedName("track_date")
        private String trackDate;
        @SerializedName("admin_id")
        private String adminId;



        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getTrackDate() {
            return trackDate;
        }

        public void setTrackDate(String trackDate) {
            this.trackDate = trackDate;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }


    }
}
