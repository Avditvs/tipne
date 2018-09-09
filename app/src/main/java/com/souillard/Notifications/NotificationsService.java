package com.souillard.Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationManagerCompat;

import com.souillard.Activities.DataBaseActivity;
import com.souillard.R;

public class NotificationsService extends JobIntentService {
    private static final int NOTIFICATION_ID = 3;

    @Override
    protected void onHandleWork(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Et le souillard ?");
        builder.setContentText("Je sais c'est dur ... mais faut l\'apprendre ...");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.vignette));
        builder.setSmallIcon(R.drawable.vignette);



        Intent notifyIntent = new Intent(this, DataBaseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
