package com.ndroid.mybroadcastreceivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyNotificationUtil {

    private int NOTIFICATION_ID = 123;

    private final String NOTIFICATION_CHANNEL_ID = "MY_NOTIFICATION_ID";
    private final String NOTIFICATION_CHANNEL_NAME = "Broadcast Message";
    private final String NOTIFICATION_CHANNEL_DESCRIPTION = "Show broadcast notification message when triggered.";

    private Context context;
    private String strTitle;
    private String strText;
    private String strBigMsg;

    public MyNotificationUtil(Context context, String strTitle, String strText, String strBigMsg, boolean shouldHaveNotificaitonId) {
        this.context = context;
        this.strTitle = strTitle;
        this.strText = strText;
        this.strBigMsg = strBigMsg;
        if (shouldHaveNotificaitonId) {
            NOTIFICATION_ID = createID();
        }
    }

    public MyNotificationUtil(Context context, String strTitle, String strText, String strBigMsg) {
        this.context = context;
        this.strTitle = strTitle;
        this.strText = strText;
        this.strBigMsg = strBigMsg;
    }

    public int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
        return id;
    }

    public void show() {
        /**Building Notification Channel*/
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        setChannelDetails(notificationManager);

        /**Config for big text (Assign BigText style notification)*/
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(strBigMsg);

        /**Building NotificationCompat*/
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_stat_alert)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(strTitle)
                .setContentText(strText)
                .setStyle(bigText)
                .setVibrate(new long[]{100, 100})
                .setOnlyAlertOnce(true);

        /**Setting notification Id and setting notification manager*/
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void setChannelDetails(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
            notificationChannel.setVibrationPattern(new long[]{100, 100});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}