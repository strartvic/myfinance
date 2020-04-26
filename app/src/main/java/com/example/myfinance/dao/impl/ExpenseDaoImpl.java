package com.example.myfinance.dao.impl;

import com.example.myfinance.dao.ExpenseDao;
import com.example.myfinance.model.Expense;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ExpenseDaoImpl extends BaseDaoImpl<Expense, UUID> implements ExpenseDao {

    public ExpenseDaoImpl(ConnectionSource connectionSource, Class<Expense> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public List<Expense> findByCategoryId(UUID categoryId) throws SQLException {
        return super.queryForEq("category_id", categoryId);
    }
}
