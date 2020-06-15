package com.example.myfinance.activity.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.myfinance.R;
import com.example.myfinance.viewmodel.dto.ExpenseDto;

public class ExpenseDialogFragment extends DialogFragment {

    public interface DialogListener {
        public void onDialogPositiveClick(ExpenseDialogFragment dialog);
    }

    private View mainView;
    private TextView expenseSumView;
    private TextView expenseDescView;

    private DialogListener dialogListener;

    private ExpenseDto expense;

    public ExpenseDialogFragment(@NonNull ExpenseDto expense) {
        this.expense = expense;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mainView = inflater.inflate(R.layout.expense_dialog, null);

        updateViews();

        builder.setView(mainView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //todo провести нормальную валидацию
                        if (expenseSumView.getText().length() == 0) {
                            return;
                        }

                        expense.setSum(Double.valueOf(expenseSumView.getText().toString()));
                        expense.setDescription(expenseDescView.getText().toString());

                        dialogListener.onDialogPositiveClick(ExpenseDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ExpenseDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void updateViews() {
        expenseSumView = (EditText) mainView.findViewById(R.id.sum);
        expenseSumView.setText(expense.getStringSum());

        expenseDescView = ((EditText) mainView.findViewById(R.id.description));
        expenseDescView.setText(expense.getDescription());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) getTargetFragment();

            if (dialogListener == null) {
                dialogListener = (DialogListener) context;
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogListener");
        }
    }

    public ExpenseDto getExpense() {
        return expense;
    }
}
