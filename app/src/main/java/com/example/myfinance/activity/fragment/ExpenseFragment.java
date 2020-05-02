package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.activity.fragment.dialog.ExpenseDialogFragment;
import com.example.myfinance.viewmodel.ExpenseViewModel;
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;
import com.example.myfinance.viewmodel.dto.ExpenseDto;

import java.util.List;

import javax.inject.Inject;

public class ExpenseFragment extends Fragment implements ExpenseDialogFragment.DialogListener {

    private static final String TAG = ExpenseFragment.class.getSimpleName();

    private ExpenseCategoryDto category;

    private TableLayout table;

    @Inject
    ExpenseViewModel expenseViewModel;

    public ExpenseFragment(ExpenseCategoryDto category) {
        this.category = category;
    }

    @Override
    public void onAttach(Context context) {
        ((MyFinanceApp) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        table = (TableLayout) inflater.inflate(R.layout.expense_fragment, container, false);

        refresh();

        return table;
    }

    @Override
    public void onDialogPositiveClick(ExpenseDialogFragment dialog) {
    }

    private void refresh() {
        TableLayout table = (TableLayout) this.table.findViewById(R.id.expense_table);
        table.removeAllViews();

        List<ExpenseDto> expenses = expenseViewModel.findByCategoryId(category.getId());

        if (expenses.isEmpty()) {
            return;
        }

        for (ExpenseDto expense : expenses) {
            TableRow row = createTableRow();

            row.addView(createText(String.valueOf(expense.getSum())));
            row.addView(createText(expense.getDescription()));
            row.addView(createText(expense.getDate().toString()));

            table.addView(row);
        }
    }

    private TableRow createTableRow() {
        ContextThemeWrapper newContext = new ContextThemeWrapper(getContext(), R.style.BodyRow);

        TableRow row = new TableRow(newContext);

        return row;
    }

    private TextView createText(String text) {
        ContextThemeWrapper newContext = new ContextThemeWrapper(getContext(), R.style.BodyText);

        TextView view = new TextView(newContext);

        view.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        view.setText(text);

        return view;
    }
}
