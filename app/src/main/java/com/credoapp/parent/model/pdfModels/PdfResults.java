package com.credoapp.parent.model.pdfModels;

import com.google.gson.annotations.SerializedName;

public class PdfResults {


    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    @SerializedName("subject")
    private String pdfName;
    @SerializedName("pdf_file")
    private String pdfFile;
}
