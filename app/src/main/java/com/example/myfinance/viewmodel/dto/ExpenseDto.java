package com.example.myfinance.viewmodel.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ExpenseDto implements Serializable {
    private UUID id;

    private double sum;

    private UUID categoryId;

    private Date date;

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

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExpenseDto{" +
                ", categoryId=" + categoryId +
                ", date=" + date +
                '}';
    }
}
