package com.example.myfinance.dao.impl;

import com.example.myfinance.dao.ExpenseCategoryDao;
import com.example.myfinance.model.ExpenseCategory;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ExpenseCategoryDaoImpl extends BaseDaoImpl<ExpenseCategory, UUID> implements ExpenseCategoryDao {

    public ExpenseCategoryDaoImpl(ConnectionSource connectionSource, Class<ExpenseCategory> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public ExpenseCategory findByName(String name) throws SQLException {
        List<ExpenseCategory> query = super.queryForEq("name", name);

        if (query.isEmpty()) {
            return null;
        }

        return query.get(0);
    }
}
