package io.hoshi.zipcode;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by hoshi on 2016/04/29.
 */
public class EditTextDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private static String BUNDLE_DIALOG_CODE = "dialog_code";
    private static String BUNDLE_SAVED_TEXT = "saved_text";

    public interface OnDismissListener {
        void onDismiss(int dialogCode, int whichButton, String text);
    }

    private OnDismissListener onDismissListener;
    private EditText editText;

    public static EditTextDialog newInstance(int dialogCode) {
        EditTextDialog dialog = new EditTextDialog();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_DIALOG_CODE, dialogCode);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDismissListener = (OnDismissListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        editText = new EditText(getContext());
        editText.setSingleLine();
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString(BUNDLE_SAVED_TEXT));
        }
        int margin = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        return new AlertDialog.Builder(getContext())
                .setTitle("Zip Code")
                .setView(editText, margin, 0, margin, 0)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_SAVED_TEXT, editText.getText().toString());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int dialogCode = getArguments().getInt(BUNDLE_DIALOG_CODE);
        onDismissListener.onDismiss(dialogCode, which, editText.getText().toString());
    }
}
