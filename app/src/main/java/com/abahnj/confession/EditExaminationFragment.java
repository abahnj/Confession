package com.abahnj.confession;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.abahnj.confession.data.ConfessionContract;


public class EditExaminationFragment extends DialogFragment {
    public static final String SIN_ID = "sin_id";
    private static final String[] EXAMINATION_COLUMNS = {
            ConfessionContract.SinEntry.COLUMN_DESCRIPTION
    };
    private int sinID;
    private OnFragmentInteractionListener mListener;
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
            sinID = arguments.getInt(SIN_ID);
        }
        //fetchExamination();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_examination, null);
        EditText editText = (EditText) view.findViewById(R.id.editETextView);
        editText.setText(fragmentBody);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view);
        return builder.create();
    }


}
