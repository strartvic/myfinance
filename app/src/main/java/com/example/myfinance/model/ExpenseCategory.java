package com.example.myfinance.model;

import com.example.myfinance.dao.impl.ExpenseCategoryRepositoryImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "expense_category", daoClass = ExpenseCategoryRepositoryImpl.class)
public class ExpenseCategory {
    @DatabaseField(generatedId = true)
    private UUID id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
