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
import com.example.myfinance.viewmodel.dto.ExpenseCategoryDto;

public class CategoryDialogFragment extends DialogFragment {

    public interface DialogListener {
        public void onDialogPositiveClick(CategoryDialogFragment dialog);
    }

    private View mainView;
    private TextView categoryNameView;
    private TextView categoryDescView;

    private DialogListener dialogListener;

    private ExpenseCategoryDto category;

    public CategoryDialogFragment(@NonNull ExpenseCategoryDto category) {
        this.category = category;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mainView = inflater.inflate(R.layout.category_dialog, null);

        updateViews();

        builder.setView(mainView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        category.setName(categoryNameView.getText().toString());
                        category.setDescription(categoryDescView.getText().toString());

                        dialogListener.onDialogPositiveClick(CategoryDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CategoryDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void updateViews() {
        categoryNameView = (EditText) mainView.findViewById(R.id.name);
        categoryNameView.setText(category.getName());

        categoryDescView = ((EditText) mainView.findViewById(R.id.description));
        categoryDescView.setText(category.getDescription());
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

    public ExpenseCategoryDto getCategory() {
        return category;
    }
}
