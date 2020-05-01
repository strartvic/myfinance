package com.example.myfinance.activity.fragment.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.myfinance.R;
import com.example.myfinance.activity.fragment.EditDialogFragment;

public class EditDialogFragmentImpl extends DialogFragment implements EditDialogFragment {

    public interface DialogListener {
        public void onDialogPositiveClick(EditDialogFragment dialog);
    }

    private View view;
    private DialogListener dialogListener;
    private String value;
    private String description;
    private int inputType = InputType.TYPE_CLASS_TEXT;

    public EditDialogFragmentImpl(Integer inputType) {
        if (inputType != null) {
            this.inputType = inputType;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog, null);

        EditText textValue = ((EditText) view.findViewById(R.id.value));
        textValue.setInputType(inputType);

        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        value = ((EditText) view.findViewById(R.id.value)).getText().toString();
                        description = ((EditText) view.findViewById(R.id.description)).getText().toString();

                        dialogListener.onDialogPositiveClick(EditDialogFragmentImpl.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditDialogFragmentImpl.this.getDialog().cancel();
                    }
                });
        return builder.create();
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

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
