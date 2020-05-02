package com.example.myfinance.repository;

import android.util.Log;

import com.example.myfinance.dao.ExpenseCategoryDao;
import com.example.myfinance.exception.EntityFoundException;
import com.example.myfinance.exception.EntityNotFoundException;
import com.example.myfinance.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExpenseCategoryRepository {

    private final String TAG = ExpenseCategoryRepository.class.getSimpleName();

    private final ExpenseCategoryDao repository;

    @Inject
    public ExpenseCategoryRepository(ExpenseCategoryDao repository) {
        this.repository = repository;
    }

    public ExpenseCategory save(ExpenseCategory category) {
        try {
            if (repository.findByName(category.getName()) != null) {
                throw new EntityFoundException(String.format("Category with name =%s existed", category.getName()));
            }

            category = repository.createIfNotExists(category);
        } catch (SQLException e) {
            String message = String.format("Category=%s don't save", category.toString());

            Log.e(TAG, message, e);
            throw new android.database.SQLException(message);
        }

        return category;
    }

    public ExpenseCategory update(ExpenseCategory category) {
        try {
            if (category.getId() == null || repository.queryForId(category.getId()) == null) {
                throw new EntityNotFoundException(String.format("Category with id =%s not existed", category.getId()));
            }

            repository.update(category);

        } catch (SQLException e) {
            String message = String.format("Category=%s don't update", category.toString());

            Log.e(TAG, message, e);
            throw new android.database.SQLException(message);
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

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (SQLException e) {
            String message = String.format("Category with id=%s don't deleted", id);

            Log.e(TAG, message, e);
            throw new EntityNotFoundException(message);
        }
    }

    public ExpenseCategory findById(UUID id) {
        try {
            return repository.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
