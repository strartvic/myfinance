package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
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
import com.example.myfinance.activity.view.CategoryView;
import com.example.myfinance.viewmodel.ExpenseCategoryViewModel;
import com.example.myfinance.viewmodel.ExpenseViewModel;
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;
import com.example.myfinance.viewmodel.dto.ExpenseDto;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class ExpenseCategoryFragment extends Fragment implements EditDialogFragmentImpl.DialogListener {

    private static final String TAG = ExpenseCategoryFragment.class.getSimpleName();

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

                DialogFragment dialog = new EditDialogFragmentImpl(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
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
        expense.setCategoryId(currentCategory.getId());
        expense.setSum(Double.valueOf(dialog.getValue()));

        expenseViewModel.save(expense);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Intent intent = new Intent(getContext(), ExpenseActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpenseActivity.EXPENSE_CATEGORY_BUNDLE, ((CategoryView) v).getExpenseCategory());

        intent.putExtras(bundle);

        startActivity(intent);
    }

}
