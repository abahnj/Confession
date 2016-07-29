package com.abahnj.confession;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * Created by abahnj on 7/26/2016.
 */
public class InspirationFragment extends DialogFragment {
    public static final String INSPIRATION_ID = "inspiration_id";
    private static final String[] INSPIRATION_COLUMNS = {
            ConfessionContract.InspirationEntry.COLUMN_AUTHOR,
            ConfessionContract.InspirationEntry.COLUMN_QUOTE
    };
    private int inspirationID;
    private OnFragmentInteractionListener mListener;
    private String fragmentTitle;
    private String fragmentBody;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            inspirationID = arguments.getInt(INSPIRATION_ID);
        }
        fetchStrings();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.inspiration_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.inspirationTextView);
        TextView textView1 = (TextView) view.findViewById(R.id.inspirationTextViewHeader);
        textView.setText(fragmentBody);
        textView1.setText(fragmentTitle);
        builder.setView(view);
        return builder.create();
    }

    private void fetchStrings(){

        Cursor cursor = getActivity().getContentResolver().query(ConfessionContract.InspirationEntry.CONTENT_URI,
                INSPIRATION_COLUMNS,
                ConfessionContract.InspirationEntry._ID + " = ?",
                new String[]{String.valueOf(inspirationID)},
                null);
        if (cursor != null && cursor.moveToFirst()) {
            fragmentTitle = cursor.getString(cursor.getColumnIndex(ConfessionContract.InspirationEntry.COLUMN_AUTHOR));
            fragmentBody = cursor.getString(cursor.getColumnIndex(ConfessionContract.InspirationEntry.COLUMN_QUOTE));
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mListener.onFragmentDismiss();
    }
}
