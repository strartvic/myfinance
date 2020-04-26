package com.example.myfinance.dao;

import com.example.myfinance.model.Expense;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ExpenseDao extends Dao<Expense, UUID> {
    List<Expense> findByCategoryId(UUID categoryId) throws SQLException;
}
