package com.credoapp.parent.model.feeModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeeModelsList {
    public List<FeeModelsInnerResults> getResults() {
        return results;
    }

    public void setResults(List<FeeModelsInnerResults> results) {
        this.results = results;
    }

    @SerializedName("exams")
    private List<FeeModelsInnerResults> results;


}
