package com.example.myfinance.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.activity.fragment.dialog.CategoryDialogFragment;
import com.example.myfinance.db.DatabaseHelper;
import com.example.myfinance.exception.EntityFoundException;
import com.example.myfinance.viewmodel.ExpenseCategoryViewModel;
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements CategoryDialogFragment.DialogListener {

    @Inject
    ExpenseCategoryViewModel categoryViewModel;

    @Inject
    DatabaseHelper databaseHelper;

    private Fragment categoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyFinanceApp) getApplicationContext()).appComponent.inject(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createAddButton();
        createDeleteButton();

        categoryFragment = getSupportFragmentManager().findFragmentById(R.id.category_fragment_id);
    }

    private void createAddButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new CategoryDialogFragment(new ExpenseCategoryDto());
                dialog.show(getSupportFragmentManager(), "edit_category");
            }
        });
    }

    private void createDeleteButton() {
        Button deleteButton = findViewById(R.id.button_delete_all);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.deleteAll();
                categoryFragment.onResume();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    public void onDialogPositiveClick(CategoryDialogFragment dialog) {
        try {
            categoryViewModel.save(dialog.getCategory());
        } catch (EntityFoundException e) {
            Snackbar.make(categoryFragment.getView(), "Категория с таким именем уже сущесвует", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        categoryFragment.onResume();
    }

}
