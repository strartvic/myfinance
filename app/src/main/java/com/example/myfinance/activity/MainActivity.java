package com.example.myfinance.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinance.MyFinanceApp;
import com.example.myfinance.R;
import com.example.myfinance.activity.fragment.EditDialogFragment;
import com.example.myfinance.adapter.ExpenseCategoryAdapter;
import com.example.myfinance.db.DatabaseHelper;
import com.example.myfinance.model.ExpenseCategory;
import com.example.myfinance.repository.ExpenseCategoryRepository;
import com.example.myfinance.view.model.ExpenseCategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.stream.Collectors;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements EditDialogFragment.CustomDialogListener {

    @Inject
    ExpenseCategoryViewModel expenseCategoryViewModel;

    @Inject
    DatabaseHelper databaseHelper;

    @Inject
    ExpenseCategoryRepository categoryService;


    RecyclerView recyclerView;

    private int rowCount = 3;
    private TableRow currentRow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyFinanceApp) getApplicationContext()).appComponent.inject(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerForContextMenu(toolbar);

        createAddButton();
        createDeleteButton();
        createRecylerView();
    }

    private void createAddButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "category save: " + category.toString(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogFragment dialog = new EditDialogFragment();
                dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            }
        });
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(getApplicationContext());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        return row;
    }

    private Button createButton(String category) {
        Button button = new Button(getApplicationContext());
        button.setLayoutParams(new TableRow.LayoutParams(250, 250));
        button.setText(category);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return button;
    }

    private void createDeleteButton() {
        Button deleteButton = findViewById(R.id.button_delete_all);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryService.deleteAll();

                ExpenseCategoryAdapter expenseCategoryAdapter = new ExpenseCategoryAdapter(categoryService.findAll().stream()
                        .map(ExpenseCategory::toString).collect(Collectors.toList()));
                recyclerView.setAdapter(expenseCategoryAdapter);
            }
        });
    }

    private void createRecylerView() {
        recyclerView = findViewById(R.id.categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ExpenseCategoryAdapter expenseCategoryAdapter = new ExpenseCategoryAdapter(expenseCategoryViewModel.findAll().stream()
                .map(ExpenseCategory::toString).collect(Collectors.toList()));
        recyclerView.setAdapter(expenseCategoryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // fragment you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 4, 0, "22");
        menu.add(0, 5, 0, "26");
        menu.add(0, 6, 0, "30");
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
    public void onDialogPositiveClick(DialogFragment dialog) {
        ExpenseCategory category = new ExpenseCategory();
        category.setName(((EditText) dialog.getDialog().getWindow().findViewById(R.id.username)).getText().toString());
        category.setDescription(((EditText) dialog.getDialog().getWindow().findViewById(R.id.description)).getText().toString());

        category = categoryService.save(category);

        ExpenseCategoryAdapter expenseCategoryAdapter = new ExpenseCategoryAdapter(categoryService.findAll().stream()
                .map(ExpenseCategory::toString).collect(Collectors.toList()));
        recyclerView.setAdapter(expenseCategoryAdapter);

        TableLayout categoryTable = findViewById(R.id.category_table);
        rowCount++;
        if (rowCount > 3) {
            rowCount = 0;
            currentRow = createTableRow();
            categoryTable.addView(currentRow);
        }

        currentRow.addView(createButton(category.getName()));
    }

}
