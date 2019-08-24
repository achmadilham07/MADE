package com.example.made.notif;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.made.MainView;
import com.example.made.R;
import com.example.made.activity.HomeActivity;
import com.example.made.api.DBApi;
import com.example.made.data.Movie;
import com.example.made.data.MovieData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ReleaseAlarmReceiver extends BroadcastReceiver implements MainView.LoadReleaseCallback {

    private final static int NOTIF_REQUEST_CODE = 200;
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    public static String CHANNEL_ID_02 = "channel_02";
    public static CharSequence CHANNEL_NAME = "belejarubic channel";
    NotificationCompat.Builder releaseBuilder;
    private NotificationManager releaseAlarmManager;
    private int maxNotif = 2;
    private int idNotif = 0;
    private ArrayList<Movie> arrayList = new ArrayList<>();
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        DBApi.doReqReleaseMovie(getDate(), this);
        this.context = context;
    }

    private String getDate() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(currentTime);
    }

    private void sendReleaseNotif(Context context, ArrayList<Movie> movies) {
        releaseAlarmManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_none);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        idNotif = movies.size();
        if (idNotif < maxNotif) {
            releaseBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_02)
                    .setContentTitle(movies.get(idNotif).getName())
                    .setContentText(String.format("%s...", movies.get(idNotif).getOverview().substring(0, 30)))
                    .setSmallIcon(R.drawable.ic_notifications_none)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .setBigContentTitle(idNotif + " new emails")
                    .setSummaryText("belajarubic@made");

            for (int i = 0; i < 5; i++) {
                inboxStyle.addLine(i + " " + movies.get(i).getName());
            }
            releaseBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_02)
                    .setContentTitle(idNotif + " new movie")
                    .setContentText("New release movie today")
                    .setSmallIcon(R.drawable.ic_notifications_none)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID_02, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

                releaseBuilder.setChannelId(CHANNEL_ID_02);

                if (releaseAlarmManager != null) {
                    releaseAlarmManager.createNotificationChannel(channel);
                }
            }

            if (releaseAlarmManager != null) {
                releaseAlarmManager.notify(idNotif, releaseBuilder.build());
            }
        }
    }

    @Override
    public void onSuccessMovie(MovieData movieResponse) {
        arrayList = movieResponse.getResults();
        idNotif = arrayList.size();
        sendReleaseNotif(context, arrayList);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}