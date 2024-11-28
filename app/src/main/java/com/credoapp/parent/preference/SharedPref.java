package com.credoapp.parent.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref implements IPreferencesHelper {

    public static SharedPref mSharedPref;
    private SharedPreferences sharedPreferences;

    private SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(IPreferencesHelper.PREF_NAME,
                Context.MODE_PRIVATE);
    }


    public static SharedPref getmSharedPrefInstance(Context context) {
        if (mSharedPref == null)
            mSharedPref = new SharedPref(context);
        return mSharedPref;
    }


    @Override
    public void saveParentId(String parentId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PARENT_ID, parentId).apply();
    }

    @Override
    public String getParentId() {
        return sharedPreferences.getString(PARENT_ID, "");
    }

    @Override
    public void saveParentName(String parentName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PARENT_NAME, parentName).apply();
    }

    @Override
    public String getParentName() {
        return sharedPreferences.getString(PARENT_NAME, "");
    }

    @Override
    public void saveDeviceKey(String deviceKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NOTIFICATION_TOKEN, deviceKey).apply();
    }

    @Override
    public String getDeviceKey() {
        return sharedPreferences.getString(NOTIFICATION_TOKEN, "");
    }

    @Override
    public void saveMobileNumber(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE_NUMBER, username).apply();
    }

    @Override
    public String getMobileNumber() {
        return sharedPreferences.getString(MOBILE_NUMBER, "");
    }

    @Override
    public void saveAdminID(String adminID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ADMIN_ID, adminID).apply();
    }

    @Override
    public String getAdminID() {
        return sharedPreferences.getString(ADMIN_ID, "");
    }

    @Override
    public void saveHeight(String height) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(heightOfScreen, height).apply();
    }

    @Override
    public String getHeightOfScreen() {
        return sharedPreferences.getString(heightOfScreen, "");
    }

    @Override
    public void saveWidth(String width) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(widthOfScreen, width).apply();
    }

    @Override
    public String getWidthOfScreen() {
        return sharedPreferences.getString(widthOfScreen, "");
    }
}