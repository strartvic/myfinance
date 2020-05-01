package com.example.myfinance.activity.view;

import android.content.Context;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatButton;

import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;

public class CategoryView extends AppCompatButton {
    private final ExpenseCategoryDto category;

    public CategoryView(Context context, ExpenseCategoryDto category) {
        super(context);
        this.category = category;

        createView();
    }

    private void createView() {
        setLayoutParams(new TableRow.LayoutParams(0, 250, 1.0f));
        setSingleLine(true);
        setText(category.getName());
    }

    public ExpenseCategoryDto getExpenseCategory() {
        return category;
    }
}
