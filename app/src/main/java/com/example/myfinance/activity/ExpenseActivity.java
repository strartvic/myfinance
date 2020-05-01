package com.example.myfinance.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfinance.R;
import com.example.myfinance.activity.fragment.ExpenseFragment;
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;

public class ExpenseActivity extends AppCompatActivity {

    public static final String EXPENSE_CATEGORY_BUNDLE = "expense";

    private ExpenseCategoryDto category;

    private ExpenseFragment expenseFragment;

    public ExpenseActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Bundle bundle = getIntent().getExtras();

        category = (ExpenseCategoryDto) bundle.getSerializable(EXPENSE_CATEGORY_BUNDLE);

        createFragment();
    }

    private void createFragment() {
        expenseFragment = new ExpenseFragment(category);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.expense_fragment_id, expenseFragment, "tag");
        fragmentTransaction.commit();
    }
}
