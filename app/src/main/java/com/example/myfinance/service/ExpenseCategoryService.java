package com.example.myfinance.service;

import com.example.myfinance.dao.ExpenseCategoryRepository;
import com.example.myfinance.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository repository;

    @Inject
    public ExpenseCategoryService(ExpenseCategoryRepository repository) {
        this.repository = repository;
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

    public void deleteAll() {
        try {
            repository.delete(repository.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
