package com.example.myfinance.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.myfinance.model.Expense;
import com.example.myfinance.repository.ExpenseCategoryRepository;
import com.example.myfinance.repository.ExpenseRepository;
import com.example.myfinance.viewmodel.dto.ExpenseDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExpenseViewModel extends ViewModel {

    private final ModelMapper mapper;

    private final ExpenseRepository repository;

    private final ExpenseCategoryRepository categoryRepository;

    @Inject
    public ExpenseViewModel(ModelMapper mapper, ExpenseRepository repository, ExpenseCategoryRepository categoryRepository) {
        super();
        this.mapper = mapper;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }


    public List<ExpenseDto> findByCategoryId(UUID categoryId) {
        Type listType = new TypeToken<List<ExpenseDto>>() {
        }.getType();

        return mapper.map(repository.findByCategoryId(categoryId), listType);
    }

    public ExpenseDto save(ExpenseDto expenseDto) {
        Expense expense = mapper.map(expenseDto, Expense.class);
        expense.setCategory(categoryRepository.findById(expenseDto.getCategoryId()));

        expense = repository.save(expense);

        return mapper.map(expense, ExpenseDto.class);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<ExpenseDto> findAll() {
        Type listType = new TypeToken<List<ExpenseDto>>() {
        }.getType();

        return mapper.map(repository.findAll(), listType);
    }
}
