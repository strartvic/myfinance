package com.example.myfinance.service;

import com.example.myfinance.db.DatabaseHelper;
import com.example.myfinance.model.ExpenseCategory;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ExpenseCategoryService {
    private Dao<ExpenseCategory, UUID> repository;

    public ExpenseCategoryService(DatabaseHelper helper) {
        try {
            repository = helper.getDao(ExpenseCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ExpenseCategory save(ExpenseCategory category) {
        try {
            category = repository.createIfNotExists(category);
        } catch (SQLException e) {
            try {
                repository.createOrUpdate(category);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new android.database.SQLException("Something wrong with database");
            }
        }

        return category;
    }

    public List<ExpenseCategory> findAll() {
        try {
            return repository.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
