package com.example.made.notif;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.made.R;
import com.example.made.activity.HomeActivity;

public class DailyAlarmReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID_01 = "channel_01";
    public static CharSequence CHANNEL_NAME = "dicoding channel";
    NotificationCompat.Builder dailyBuilder;
    private NotificationManager dailyAlarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        sendDailyNotif(context);
    }

    private void sendDailyNotif(Context context) {
        dailyAlarmManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, HomeActivity.class), 0);

        dailyBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_01)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_notifications_none)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_none))
                .setContentTitle(context.getResources().getString(R.string.content_title))
                .setContentText(context.getResources().getString(R.string.daily_summary))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_01, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            dailyBuilder.setChannelId(CHANNEL_ID_01);

            if (dailyAlarmManager != null) {
                dailyAlarmManager.createNotificationChannel(channel);
            }
        }

        if (dailyAlarmManager != null) {
            dailyAlarmManager.notify(NOTIFICATION_ID, dailyBuilder.build());
        }
    }
}