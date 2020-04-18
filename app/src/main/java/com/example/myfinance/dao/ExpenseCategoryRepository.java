package com.example.myfinance.dao;

import com.example.myfinance.model.ExpenseCategory;
import com.j256.ormlite.dao.Dao;

import java.util.UUID;

public interface ExpenseCategoryRepository extends Dao<ExpenseCategory, UUID> {
}
