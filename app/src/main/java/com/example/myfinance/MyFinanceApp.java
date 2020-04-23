package com.example.myfinance;

import android.app.Application;

import com.example.myfinance.module.MyFinanceModule;

public class MyFinanceApp extends Application {
    public ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .myFinanceModule(new MyFinanceModule(this))
            .build();
}
