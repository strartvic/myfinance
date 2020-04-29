package com.example.myfinance.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.activity.fragment.impl.EditDialogFragmentImpl;
import com.example.myfinance.adapter.ExpenseCategoryAdapter;
import com.example.myfinance.view.dto.ExpenseDto;
import com.example.myfinance.view.model.ExpenseViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ExpenseFragment extends Fragment implements EditDialogFragmentImpl.DialogListener {

    private static final String TAG = ExpenseFragment.class.getSimpleName();

    @Inject
    ExpenseViewModel expenseViewModel;

    private RecyclerView recyclerView;
    private Map<String, UUID> mapCategories = new HashMap<>();

    @Override
    public void onAttach(Context context) {
        ((MyFinanceApp) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.expense_fragment, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ExpenseCategoryAdapter expenseCategoryAdapter = new ExpenseCategoryAdapter(expenseViewModel.findAll().stream()
                .map(ExpenseDto::toString).collect(Collectors.toList()));
        recyclerView.setAdapter(expenseCategoryAdapter);

        return recyclerView;
    }

    @Override
    public void onDialogPositiveClick(EditDialogFragment dialog) {
        ExpenseDto expense = new ExpenseDto();
        expense.setDate(new Date());
        expense.setCategoryId(mapCategories.get(dialog.getDescription()));
        expense.setSum(Double.valueOf(dialog.getValue()));

        Log.i(TAG, "Expense created");
    }
}
