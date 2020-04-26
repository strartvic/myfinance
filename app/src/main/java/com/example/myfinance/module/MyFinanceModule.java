package com.example.myfinance.module;

import android.content.Context;

import com.example.myfinance.dao.ExpenseCategoryDao;
import com.example.myfinance.db.DatabaseHelper;
import com.example.myfinance.model.ExpenseCategory;

import org.modelmapper.ModelMapper;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyFinanceModule {
    private final Context context;

    public MyFinanceModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(context);
    }

    @Singleton
    @Provides
    public ExpenseCategoryDao provideExpenseCategoryDao(DatabaseHelper databaseHelper) {
        try {
            return (ExpenseCategoryDao) databaseHelper.getDao(ExpenseCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Singleton
    @Provides
    public ModelMapper provideModelMapper() {
        return new ModelMapper();
    }
}