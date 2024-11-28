package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeeModelsInnerResults {

    @SerializedName("examCategory")
    private String examCategory;
    @SerializedName("timeTables")
    private List<FeeModelsInner> feeModelsInners;

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public List<FeeModelsInner> getFeeModelsInners() {
        return feeModelsInners;
    }

    public void setFeeModelsInners(List<FeeModelsInner> feeModelsInners) {
        this.feeModelsInners = feeModelsInners;
    }

}
