package com.example.myfinance.dao;

import com.example.myfinance.model.ExpenseCategory;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ExpenseCategoryDao extends Dao<ExpenseCategory, UUID> {
    List<ExpenseCategory> findByName(String name) throws SQLException;
}
