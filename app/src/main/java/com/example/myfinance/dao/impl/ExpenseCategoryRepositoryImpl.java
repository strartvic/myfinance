package com.example.myfinance.dao.impl;

import com.example.myfinance.dao.ExpenseCategoryRepository;
import com.example.myfinance.model.ExpenseCategory;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ExpenseCategoryRepositoryImpl extends BaseDaoImpl<ExpenseCategory, UUID> implements ExpenseCategoryRepository {

    public ExpenseCategoryRepositoryImpl(ConnectionSource connectionSource, Class<ExpenseCategory> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public List<ExpenseCategory> findByName(String name) throws SQLException {
        return super.queryForEq("name", name);
    }
}
