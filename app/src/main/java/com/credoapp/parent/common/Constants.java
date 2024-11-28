package com.credoapp.parent.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.widget.Toast;
import com.credoapp.parent.R;

import java.util.Objects;

public class Constants {

    public static final String USER_ID = "userId";
    public static final String USER_IDD = "user";
    public static final String PARENT_ID = "parent_id";
    public static final String PARENT_NAME = "parent_name";
    public static final String ADMIN_ID = "admin_id";
    public static final String PARENT_NUMBER = "parent_no";
    public static final String CLASS_ID = "classId";
    public static String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String MobilePattern = "[6-9][0-9]{9}";
    public static String PREFERENCE_NAME = "itutorParent";
    public static final String DEFAULTACADEMIC_ID = "academicId";
    public static final String DEFAULTACADEMIC_NAME = "academinName";

    public static boolean haveInternet(Context ctx) {
        try {
            NetworkInfo info = ((ConnectivityManager) Objects.requireNonNull(ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE)))
                    .getActiveNetworkInfo();

            if (info == null || !info.isConnected()) {
                return false;
            }
        } catch (Exception e) {
            Log.d("err", e.toString());
        }
        return true;
    }


    public static void InternetSettings(final Context ctx) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder
                .setMessage("No internet connection on your device. Would you like to enable it?")
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton(" Enable Internet ",
                        (dialog, id) -> {
                            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(dialogIntent);

                        });

        alertDialogBuilder.setNegativeButton(" Cancel ", (dialog, id) -> dialog.cancel());

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public static void IntenetSettings(final Context ctx) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder
                .setMessage(R.string.noInternet)
                .setTitle(R.string.noInternetConnection)
                .setCancelable(false)
                .setPositiveButton(" Enable Internet ",
                        (dialog, id) -> {
                            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(dialogIntent);

                        });

        alertDialogBuilder.setNegativeButton(" Cancel ", (dialog, id) -> dialog.cancel());

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

//    public static String getCurrentTime() {
//        DateFormat dateFormat = new SimpleDateFormat("HH");
//        Calendar cal = Calendar.getInstance();
//        return dateFormat.format(cal.getTime());
//    }


    public static void ToastShort(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

}
