package com.credoapp.parent.model.addAssignment;


public class AddAssignmentModel {


    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgId;
    private String imgUrl;

    public AddAssignmentModel(String imgId, String imgUrl) {
        this.imgId = imgId;
        this.imgUrl = imgUrl;
    }
}
