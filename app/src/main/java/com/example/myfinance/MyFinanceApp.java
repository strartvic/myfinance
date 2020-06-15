package com.example.myfinance;

import android.app.Application;

import com.example.myfinance.module.DatabaseModule;
import com.example.myfinance.module.MapperModule;
import com.example.myfinance.module.ServiceModule;

public class MyFinanceApp extends Application {
    public ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .databaseModule(new DatabaseModule(this))
            .mapperModule(new MapperModule(this))
            .serviceModule(new ServiceModule(this))
            .build();
}
