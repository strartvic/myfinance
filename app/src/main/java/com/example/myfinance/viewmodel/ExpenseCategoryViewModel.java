package com.example.myfinance.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.myfinance.model.ExpenseCategory;
import com.example.myfinance.repository.ExpenseCategoryRepository;
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExpenseCategoryViewModel extends ViewModel {

    private final ModelMapper mapper;

    private final ExpenseCategoryRepository repository;

    @Inject
    public ExpenseCategoryViewModel(ModelMapper mapper, ExpenseCategoryRepository repository) {
        super();
        this.mapper = mapper;
        this.repository = repository;
    }


    public List<ExpenseCategoryDto> findAll() {
        Type listType = new TypeToken<List<ExpenseCategoryDto>>() {
        }.getType();

        return mapper.map(repository.findAll(), listType);
    }

    public ExpenseCategoryDto save(ExpenseCategoryDto expenseCategoryDto) {
        ExpenseCategory category = mapper.map(expenseCategoryDto, ExpenseCategory.class);

        if (expenseCategoryDto.getId() == null) {
            category = repository.save(category);
        } else {
            category = repository.update(category);
        }

        return mapper.map(category, ExpenseCategoryDto.class);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

    public ExpenseCategoryDto update(ExpenseCategoryDto dto) {
        ExpenseCategory category = mapper.map(dto, ExpenseCategory.class);

        category = repository.update(category);

        return mapper.map(category, ExpenseCategoryDto.class);
    }
}
