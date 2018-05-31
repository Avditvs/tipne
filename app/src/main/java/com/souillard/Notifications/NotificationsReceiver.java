package com.souillard.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.souillard.R;

public class NotificationsReceiver extends BroadcastReceiver {

    public NotificationsReceiver(){
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, NotificationsService.class);
        context.startService(intent1);
    }
}
