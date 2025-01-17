package com.example.myfinance.viewmodel.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ExpenseDto implements Serializable {
    private UUID id;

    private Double sum;

    private UUID categoryId;

    private Date date;

    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExpenseDto{" +
                ", categoryId=" + categoryId +
                ", date=" + date +
                '}';
    }

    public String getStringSum() {
        return sum == null ? "" : sum.toString();
    }
}
