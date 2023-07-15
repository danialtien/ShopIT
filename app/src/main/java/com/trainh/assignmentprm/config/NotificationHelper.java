package com.trainh.assignmentprm.config;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.activity.MyHomeActivity;
import com.trainh.assignmentprm.activity.MyNotificationActivity;

public class NotificationHelper {
    public static void SendNotification(Context context, String title, String body){
        final  String CHANNEL_ID = "channel", CHANNEL_NAME = "Notification";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Intent activityYesIntent = new Intent(context, MyNotificationActivity.class);
        PendingIntent playContentIntent = PendingIntent.getActivity(context, 0, activityYesIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent activityCancelIntent = new Intent(context, MyHomeActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(context, 0, activityCancelIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder  builder= new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(body)
                .addAction(R.drawable.notification_icon, "View", playContentIntent)
                .addAction(R.drawable.notification_icon, "Cancel", cancelContentIntent)
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(null);

        MediaPlayer notificationSound = MediaPlayer.create(context, R.raw.notification);
        notificationSound.start();

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        notificationManager.notify(1, builder.build());
    }
}
