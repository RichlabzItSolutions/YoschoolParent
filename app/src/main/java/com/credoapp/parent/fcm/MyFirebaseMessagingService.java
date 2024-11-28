package com.credoapp.parent.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.ui.SplashScreen;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.credoapp.parent.R;

import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    MediaPlayer player;
    Bitmap image;
    NotificationCompat.Builder notificationBuilder;
    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);
        Log.d(TAG, "New Token : " + s);

        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(getApplicationContext());
        sharedPref.saveDeviceKey(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        Log.e(TAG, "onMessageReceived: " );
//        if (notification != null) {
//            Intent intent = new Intent("anag.new_notification");
//            intent.putExtra("title", notification.getTitle());
//            intent.putExtra("body", notification.getBody());
//            intent.putExtra("time", remoteMessage.getSentTime());
//            sendBroadcast(intent);
//        } else {
        Map<String, String> data = remoteMessage.getData();
//        Log.d(TAG, data.get("title"));
//        Log.d(TAG, data.get("body"));
//        Log.d(TAG, data.get("type"));
        if (data.get("title")==null){
            return;
        }else {
            Log.d(TAG, "===========>: ]"+data.get("title")+"  "+"   ,   "+data.get("body")+"     ,     "+data.get("type")+"   ,   "+data.get("parent_id"));
            Log.d(TAG, "title: "+data.get("title"));
        }
        if (data.get("body")==null){
            return;
        }else {
            Log.d(TAG, "body: "+data.get("body"));
        }
        if (data.get("type")==null){
            return;
        }else {
            Log.d(TAG, "type: "+data.get("type"));
        }
        if (data.get("parent_id")==null){
            return;
        }else {
            Log.d(TAG, "parent_id: "+data.get("parent_id"));
        }
        if (data.get("parent_id")==null){
            return;
        }else {
            Log.d(TAG, "parent_id: "+data.get("parent_id"));
        }
        if (data.get("class_id")==null){
            return;
        }else {
            Log.d(TAG, "class_id: "+data.get("class_id"));
        }
//        Log.d("studentId=====>", data.get("student_id"));
//        Log.d("parent_id=====>", data.get("parent_id"));
//        Log.d("class_id=====>", data.get("class_id"));


//        CommonMusic.SoundPlayer();

        String imageStatus = null;


        if (Objects.requireNonNull(data.get("body")).startsWith("http")){
            imageStatus = "1";
        }else {
            imageStatus = "2";
        }

        sendNotification(data.get("title"), data.get("body"), data.get("type"), data.get("student_id"), data.get("parent_id"), data.get("class_id"),imageStatus);

    }

    private void sendNotification(String title, String body, String type, String student_id, String parent_id, String class_id, String imageStatus) {
        long notificationId = System.currentTimeMillis();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        intent.putExtra("cameFrom","push");
        intent.putExtra("type", type);
        intent.putExtra("student_id", student_id);
        intent.putExtra("parent_id", parent_id);
        intent.putExtra("class_id", class_id);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager mNotificationManage =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            Objects.requireNonNull(mNotificationManage).createNotificationChannel(channel);
        }

        Log.e(TAG, "imageStatus: "+imageStatus );


//        if (imageStatus.equals("1")) {
//            try {
//                Log.d(TAG, "sendNotification: " + body);
//                URL url = new URL(body);
//                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//
//            notificationBuilder = new NotificationCompat.Builder(this, "default")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(this.getResources().getString(R.string.app_name))
//                    .setContentTitle(title)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(image))
//                    .setAutoCancel(true)
//                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
//                    .setContentIntent(contentIntent);
//        } else {
//            notificationBuilder = new NotificationCompat.Builder(this, "default")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(this.getResources().getString(R.string.app_name))
//                    .setContentTitle(title)
//                    .setContentText(body)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
//                    .setAutoCancel(true)
//                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
//                    .setContentIntent(contentIntent);
//        }

        if (imageStatus.equals("1")) {
            body = "You got a notification from Credo Parent App";
        }
        Log.e(TAG, "sendNotification: "+body);
        notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(this.getResources().getString(R.string.app_name))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorPrimaryDark))
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(mNotificationManager).notify((int) notificationId, notificationBuilder.build());

    }
}
