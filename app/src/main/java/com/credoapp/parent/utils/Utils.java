package com.credoapp.parent.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {


    private static ProgressDialog progressBar;

    public static void showProgressBar(Context context) {

        progressBar = new ProgressDialog(context);
        progressBar.setMessage("Loading");
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressBar.show();
    }


    public static void hideProgressBar() {
        if (progressBar != null && progressBar.isShowing()) progressBar.dismiss();
    }
}
