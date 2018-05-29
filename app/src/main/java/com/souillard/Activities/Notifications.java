package com.souillard.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.souillard.R;

public class Notifications extends BroadcastReceiver {

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, DataBaseActivity.class), 0);
        Notification.Builder builder = new Notification.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setTicker("Et le souillard ?")
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("Tu ne l\'as pas oublié quand même ?")
                .setContentText("Je sais c'est dur, mais il faut l\'apprendre ...")
                .setContentIntent(pendingIntent);

        nm.notify(1, builder.build());

    }
}
