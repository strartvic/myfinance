package com.example.myfinance;

import com.example.myfinance.activity.MainActivity;
import com.example.myfinance.activity.fragment.ExpenseCategoryFragment;
import com.example.myfinance.module.MyFinanceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MyFinanceModule.class)
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(ExpenseCategoryFragment expenseCategoryFragment);
}