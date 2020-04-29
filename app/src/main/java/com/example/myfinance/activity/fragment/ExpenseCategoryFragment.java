package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.activity.ExpenseActivity;
import com.example.myfinance.activity.fragment.impl.EditDialogFragmentImpl;
import com.example.myfinance.view.dto.ExpenseCategoryDto;
import com.example.myfinance.view.dto.ExpenseDto;
import com.example.myfinance.view.model.ExpenseCategoryViewModel;
import com.example.myfinance.view.model.ExpenseViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

public class ExpenseCategoryFragment extends Fragment implements EditDialogFragmentImpl.DialogListener {

    private static final String TAG = ExpenseCategoryFragment.class.getSimpleName();

    private static final int MAX_COUNT_CATEGORIES_IN_ROW = 4;

    @Inject
    ExpenseCategoryViewModel categoryViewModel;
    @Inject
    ExpenseViewModel expenseViewModel;

    private TableLayout table;
    private Map<String, UUID> mapCategories = new HashMap<>();
    private String currentCategoryName;

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

            mapCategories.put(category.getName(), category.getId());

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
                currentCategoryName = ((Button) view).getText().toString();

                DialogFragment dialog = new EditDialogFragmentImpl();
                dialog.setTargetFragment(ExpenseCategoryFragment.this, 1);
                dialog.show(getFragmentManager(), "expense_dialog");
            }
        });

        registerForContextMenu(button);

        return button;
    }

    private void createEmptyText(TableRow row) {
        TextView text = new TextView(getContext());
        text.setLayoutParams(new TableRow.LayoutParams(0, 250, 1.0f));
        row.addView(text);
    }

    @Override
    public void onDialogPositiveClick(EditDialogFragment dialog) {
        ExpenseDto expense = new ExpenseDto();
        expense.setDate(new Date());
        expense.setCategoryId(mapCategories.get(currentCategoryName));
        expense.setSum(Double.valueOf(dialog.getValue()));

        expenseViewModel.save(expense);

        Log.i(TAG, "Expense created");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Intent intent = new Intent(getContext(), ExpenseActivity.class);
        startActivity(intent);
    }

}
