package com.example.myfinance;

import com.example.myfinance.activity.ExpenseActivity;
import com.example.myfinance.activity.MainActivity;
import com.example.myfinance.activity.fragment.ExpenseCategoryFragment;
import com.example.myfinance.activity.fragment.ExpenseFragment;
import com.example.myfinance.module.MyFinanceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MyFinanceModule.class)
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    void inject(ExpenseActivity expenseActivity);

    void inject(ExpenseCategoryFragment expenseCategoryFragment);

    void inject(ExpenseFragment expenseFragment);
}