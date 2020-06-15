package com.example.myfinance.module;

import android.content.Context;

import org.modelmapper.ModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapperModule {
    private final Context context;

    public MapperModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public ModelMapper provideModelMapper() {
        return new ModelMapper();
    }
}