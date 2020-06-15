package com.example.myfinance.module;

import android.content.Context;

import com.example.myfinance.dao.ExpenseCategoryDao;
import com.example.myfinance.dao.ExpenseDao;
import com.example.myfinance.db.DatabaseHelper;
import com.example.myfinance.model.Expense;
import com.example.myfinance.model.ExpenseCategory;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private final Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public DatabaseHelper provideDatabaseHelper() {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
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
    public ExpenseDao provideExpenseDao(DatabaseHelper databaseHelper) {
        try {
            return (ExpenseDao) databaseHelper.getDao(Expense.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}