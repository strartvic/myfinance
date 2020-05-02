package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.activity.ExpenseActivity;
import com.example.myfinance.activity.fragment.dialog.CategoryDialogFragment;
import com.example.myfinance.activity.fragment.dialog.ExpenseDialogFragment;
import com.example.myfinance.activity.view.CategoryView;
import com.example.myfinance.viewmodel.ExpenseCategoryViewModel;
import com.example.myfinance.viewmodel.ExpenseViewModel;
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;
import com.example.myfinance.viewmodel.dto.ExpenseDto;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class ExpenseCategoryFragment extends Fragment implements ExpenseDialogFragment.DialogListener {

    private static final String TAG = ExpenseCategoryFragment.class.getSimpleName();

    private static final String MENU_EXPENSE = "Расходы";

    private static final String MENU_EDIT_CATEGORY = "Редактировать";

    private static final String MENU_DELETE = "Удалить";

    private static final int MAX_COUNT_CATEGORIES_IN_ROW = 4;

    @Inject
    ExpenseCategoryViewModel categoryViewModel;
    @Inject
    ExpenseViewModel expenseViewModel;

    private ScrollView scrollView;
    private TableLayout table;
    private ExpenseCategoryDto currentCategory;

    @Override
    public void onAttach(Context context) {
        ((MyFinanceApp) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scrollView = (ScrollView) inflater.inflate(R.layout.category_fragment, container, false);
        table = (TableLayout) scrollView.findViewById(R.id.category_table);

        refresh();

        return scrollView;
    }

    @Override
    public void onResume() {
        refresh();

        super.onResume();
    }

    private void refresh() {
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

            row.addView(createCategoryButton(category));

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

    private CategoryView createCategoryButton(ExpenseCategoryDto category) {
        CategoryView button = new CategoryView(getContext(), category);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = ((CategoryView) view).getExpenseCategory();

                createExpense(currentCategory.getId());
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
    public void onDialogPositiveClick(ExpenseDialogFragment dialog) {
        expenseViewModel.save(dialog.getExpense());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        currentCategory = ((CategoryView) v).getExpenseCategory();

        menu.add(MENU_EXPENSE);
        menu.add(MENU_EDIT_CATEGORY);
        menu.add(MENU_DELETE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String title = item.getTitle().toString();

        if (MENU_EXPENSE.equals(title)) {
            showExpenseActivityForCurrentCategory();
        }

        if (MENU_EDIT_CATEGORY.equals(title)) {
            editCurrentCategory();
        }

        if (MENU_DELETE.equals(title)) {
            deleteCurrentCategory();
        }

        return super.onContextItemSelected(item);
    }

    private void editCurrentCategory() {
        CategoryDialogFragment dialog = new CategoryDialogFragment(currentCategory);

        dialog.show(getFragmentManager(), "category_dialog");
    }

    private void createExpense(UUID categoryId) {
        ExpenseDto expense = new ExpenseDto();
        expense.setCategoryId(categoryId);

        ExpenseDialogFragment dialog = new ExpenseDialogFragment(expense);
        dialog.setTargetFragment(ExpenseCategoryFragment.this, 1);
        dialog.show(getFragmentManager(), "expense_dialog");
    }

    private void showExpenseActivityForCurrentCategory() {
        Intent intent = new Intent(getContext(), ExpenseActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpenseActivity.EXPENSE_CATEGORY_BUNDLE, currentCategory);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void deleteCurrentCategory() {
        categoryViewModel.delete(currentCategory.getId());

        refresh();
    }
}
