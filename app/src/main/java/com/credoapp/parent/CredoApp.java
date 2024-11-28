package com.credoapp.parent;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.credoapp.parent.common.Constants;
import com.google.firebase.FirebaseApp;

public class CredoApp extends Application {

    private static CredoApp credoApp;
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        FirebaseApp.initializeApp(this);
    }
    public static Context getAppContext() {
        return CredoApp.context;
    }

    public String getAcedemicId() {

        SharedPreferences sp = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getString(Constants.DEFAULTACADEMIC_ID, "-1");
        //  String name = sp.getString(Constants.DEFAULTACADEMIC_ID, "default value");
    }
}
