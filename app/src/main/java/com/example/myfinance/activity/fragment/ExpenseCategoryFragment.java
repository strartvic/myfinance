package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.view.dto.ExpenseCategoryDto;
import com.example.myfinance.view.model.ExpenseCategoryViewModel;

import java.util.List;

import javax.inject.Inject;

public class ExpenseCategoryFragment extends Fragment {

    private static final int MAX_COUNT_CATEGORIES_IN_ROW = 4;

    @Inject
    ExpenseCategoryViewModel categoryViewModel;

    private TableLayout table;

    @Override
    public void onAttach(Context context) {
        ((MyFinanceApp) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        table = (TableLayout) inflater.inflate(R.layout.category_fragment, container, false);

        refreshCategories();

        return table;
    }

    private void refreshCategories() {
        table.removeAllViews();

        List<ExpenseCategoryDto> categories = categoryViewModel.findAll();

        int rowCount = 0;
        TableRow row = null;
        for (ExpenseCategoryDto category : categories) {
            if (row == null || rowCount == MAX_COUNT_CATEGORIES_IN_ROW) {
                row = createTableRow();
                table.addView(row);
                rowCount = 0;
            }

            row.addView(createCategoryButton(category.getName()));

            rowCount++;
        }
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        return row;
    }

    private Button createCategoryButton(String category) {
        Button button = new Button(getContext());
        button.setLayoutParams(new TableRow.LayoutParams(250, 250));
        button.setText(category);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return button;
    }

}
