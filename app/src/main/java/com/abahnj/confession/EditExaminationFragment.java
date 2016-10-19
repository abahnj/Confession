package com.abahnj.confession;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.abahnj.confession.data.ConfessionContract;


public class EditExaminationFragment extends DialogFragment {

    public static final String ROW_ID = "row_id";
    private static final String[] EXAMINATION_COLUMNS = {
            ConfessionContract.SinEntry.COLUMN_DESCRIPTION
    };
    private int sinID;
    private EditText editText;
    private OnSaveClicked mListener;
    private String fragmentBody;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSaveClicked) {
            mListener = (OnSaveClicked) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSaveClicked");
        }

        Bundle arguments = getArguments();
        if (arguments != null) {
            sinID = arguments.getInt(ROW_ID);
        }
        //fetchExamination();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        fetchStrings();
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_examination, null);
        editText = (EditText) view.findViewById(R.id.editETextView);
        editText.setText(fragmentBody);
        builder.setTitle(R.string.edit_examination_dialog_title);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStrings();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    private void fetchStrings() {

        Cursor cursor = getActivity().getContentResolver().query(ConfessionContract.SinEntry.CONTENT_URI,
                EXAMINATION_COLUMNS,
                ConfessionContract.SinEntry._ID + " = ?",
                new String[]{String.valueOf(sinID)},
                null);
        if (cursor != null && cursor.moveToFirst()) {
            fragmentBody = cursor.getString(cursor.getColumnIndex(ConfessionContract.SinEntry.COLUMN_DESCRIPTION));
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void updateStrings() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConfessionContract.SinEntry.COLUMN_DESCRIPTION, editText.getText().toString());

        int updated = getActivity().getContentResolver().update(ConfessionContract.SinEntry.CONTENT_URI,
                contentValues,
                ConfessionContract.SinEntry._ID + " = ?",
                new String[]{String.valueOf(sinID)});
        if (updated != 0) {
            mListener.onFragmentDismiss();
        }
    }

    public interface OnSaveClicked {
        void onFragmentDismiss();
    }
}
