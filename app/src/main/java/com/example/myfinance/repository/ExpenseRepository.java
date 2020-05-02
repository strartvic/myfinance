package com.example.myfinance.repository;

import android.util.Log;

import com.example.myfinance.dao.ExpenseDao;
import com.example.myfinance.model.Expense;
import com.example.myfinance.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExpenseRepository {

    private final String TAG = ExpenseCategory.class.getSimpleName();

    private final ExpenseDao dao;

    @Inject
    public ExpenseRepository(ExpenseDao dao) {
        this.dao = dao;
    }

    public Expense save(Expense expense) {
        try {
            if (expense.getDate() == null) {
                expense.setDate(new Date());
            }

            expense = dao.createIfNotExists(expense);
        } catch (SQLException e) {
            try {
                dao.createOrUpdate(expense);
            } catch (SQLException ex) {
                String message = String.format("Expense=%s don't save", expense.toString());

                Log.e(TAG, message, e);
                throw new android.database.SQLException(message);
            }
        }

        return expense;
    }

    public List<Expense> findByCategoryId(UUID categoryId) {
        try {
            return dao.findByCategoryId(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void deleteAll() {
        try {
            dao.delete(dao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> findAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
