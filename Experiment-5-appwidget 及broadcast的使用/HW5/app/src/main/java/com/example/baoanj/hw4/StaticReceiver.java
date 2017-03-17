package com.example.baoanj.hw4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by baoanj on 2016/10/24.
 */
public class StaticReceiver extends BroadcastReceiver {

    private static final String STATICACTION = "com.example.hw4.staticreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)) {
            Bundle bundle = intent.getExtras();
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), bundle.getInt("imageId"));
            builder.setContentTitle("静态广播")
                    .setContentText(bundle.getString("name"))
                    .setTicker("hello")
                    .setLargeIcon(bm)
                    .setSmallIcon(bundle.getInt("imageId"))
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

            Intent mIntent = new Intent(context, MainActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
            builder.setContentIntent(mPendingIntent);
            Notification notify = builder.build();
            manager.notify(0, notify);
        }
    }
}
