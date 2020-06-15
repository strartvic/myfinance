package com.example.myfinance.module;

import android.content.Context;

import com.example.myfinance.notification.NotificationService;
import com.example.myfinance.notification.impl.NotificationServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    private final Context context;

    public ServiceModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public NotificationService provideNotificationService() {
        return new NotificationServiceImpl(context);
    }
}