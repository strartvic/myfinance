package com.example.myfinance.repository;

import com.example.myfinance.dao.ExpenseDao;
import com.example.myfinance.model.Expense;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExpenseRepository {
    private final ExpenseDao dao;

    @Inject
    public ExpenseRepository(ExpenseDao dao) {
        this.dao = dao;
    }

    public Expense save(Expense expense) {
        try {
            expense = dao.createIfNotExists(expense);
        } catch (SQLException e) {
            try {
                dao.createOrUpdate(expense);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new android.database.SQLException("Something wrong with database");
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
