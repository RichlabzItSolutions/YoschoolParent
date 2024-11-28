package com.credoapp.parent.preference;

public interface IPreferencesHelper {
    String PREF_NAME = "parent_app_pref";

    String PARENT_ID = "parent_id";
    String PARENT_NAME = "parent_name";
    String NOTIFICATION_TOKEN = "device_key";
    String MOBILE_NUMBER = "mobile_number";

    String ADMIN_ID = "admin_id";

    String heightOfScreen = "heightOfScreen";
    String widthOfScreen = "widthOfScreen";

    void saveParentId(String parentId);

    String getParentId();


    void saveParentName(String parentName);

    String getParentName();

    void saveDeviceKey(String deviceKey);

    String getDeviceKey();


    void saveMobileNumber(String username);

    String getMobileNumber();

    void saveAdminID(String adminID);

    String getAdminID();


    void saveHeight(String height);

    String getHeightOfScreen();

    void saveWidth(String width);

    String getWidthOfScreen();

}