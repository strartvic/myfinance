package com.example.myfinance.module;

import android.content.Context;

import com.example.myfinance.dao.ExpenseCategoryRepository;
import com.example.myfinance.db.DatabaseHelper;
import com.example.myfinance.model.ExpenseCategory;

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
    public ExpenseCategoryRepository provideExpenseCategoryRepository(DatabaseHelper databaseHelper) {
        try {
            return (ExpenseCategoryRepository) databaseHelper.getDao(ExpenseCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}