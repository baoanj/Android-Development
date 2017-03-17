package com.example.baoanj.hw4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by baoanj on 2016/10/24.
 */
public class DynamicReceiver extends BroadcastReceiver {

    private static final String DYNAMICACTION = "com.example.hw4.dynamicreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {
            Bundle bundle = intent.getExtras();
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), bundle.getInt("imageId"));
            builder.setContentTitle("动态广播")
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

            // Widget
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
            rv.setImageViewResource(R.id.appwidget_image, bundle.getInt("imageId"));
            rv.setTextViewText(R.id.appwidget_text, bundle.getString("name"));

            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            int[] appIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetDemo.class));
            appWidgetManager.updateAppWidget(appIds, rv);
        }
    }
}
