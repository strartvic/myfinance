package com.example.myfinance.notification.impl;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myfinance.R;
import com.example.myfinance.notification.NotificationService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationServiceImpl implements NotificationService {

    private final static String CHANNEL_ID = "myfinance_channel";

    private final NotificationManagerCompat manager;
    private final Context context;

    private int notificationId = 0;

    @Inject
    public NotificationServiceImpl(Context context) {
        this.manager = NotificationManagerCompat.from(context);
        this.context = context;
    }

    @Override
    public void createNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(getNewNotificationId(), builder.build());
    }

    private int getNewNotificationId() {
        return notificationId++;
    }
}
