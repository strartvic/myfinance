package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    @Override
    public void onResume() {
        refreshCategories();

        super.onResume();
    }

    private void refreshCategories() {
        table.removeAllViews();

        List<ExpenseCategoryDto> categories = categoryViewModel.findAll();

        if (categories.isEmpty()) {
            return;
        }

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

        for (int i = 0; i < MAX_COUNT_CATEGORIES_IN_ROW - rowCount; i++) {
            createEmptyText(row);
        }
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        return row;
    }

    private Button createCategoryButton(String categoryName) {
        Button button = new Button(getContext());
        button.setLayoutParams(new TableRow.LayoutParams(0, 250, 1.0f));
        button.setSingleLine(true);
        button.setText(categoryName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return button;
    }

    private void createEmptyText(TableRow row) {
        TextView text = new TextView(getContext());
        text.setLayoutParams(new TableRow.LayoutParams(0, 250, 1.0f));
        row.addView(text);
    }


}
