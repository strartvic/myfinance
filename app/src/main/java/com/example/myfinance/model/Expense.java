package com.example.myfinance.model;

import com.example.myfinance.dao.impl.ExpenseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "expenses", daoClass = ExpenseDaoImpl.class)
public class Expense {
    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(canBeNull = false)
    private double sum;

    @DatabaseField(columnName = "category_id", canBeNull = false, foreign = true)
    private ExpenseCategory category;

    @DatabaseField(canBeNull = false)
    private Date date;

    @DatabaseField
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", sum=" + sum +
                (category == null ? "" : ", category=" + category.toString()) +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
