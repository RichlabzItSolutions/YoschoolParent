package com.credoapp.parent.model.appVersionModels;

import com.google.gson.annotations.SerializedName;

public class AppVersionResponse {

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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    private  String code;
    private  String message;
    private  String description;
    @SerializedName("force_key")
    private String appVersion;


}
