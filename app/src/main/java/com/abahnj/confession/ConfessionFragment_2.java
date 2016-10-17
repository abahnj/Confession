package com.abahnj.confession;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConfessionFragment_2 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int FRAGMENT_TAG = 2;
    private static final int CONFESSION_LOADER = 2;// identifies Loader
    private static final String[] CONFESSION_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            ConfessionContract.SinActiveEntry.TABLE_NAME + "." + ConfessionContract.SinActiveEntry._ID,
            ConfessionContract.SinActiveEntry.TABLE_NAME + "." + ConfessionContract.SinActiveEntry.COLUMN_DESCRIPTION,
            ConfessionContract.PersonToSinEntry.TABLE_NAME + "." + ConfessionContract.PersonToSinEntry.COLUMN_PERSON_ID,
            ConfessionContract.PersonToSinEntry.TABLE_NAME + "." + ConfessionContract.PersonToSinEntry.COLUMN_COUNT

    };
    private String selection;
    private String[] selectionArgs;
    private ConfessionAdapter confessionAdapter;
    private OnFragmentInteractionListener mListener;
    private TextView emptyTextView;

    public ConfessionFragment_2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences user = getActivity().getSharedPreferences(PREFS_NAME, 0);
        int vocation = user.getInt("vocation", 99);
        long dob = user.getLong("birthDate", 99);
        int sex = user.getInt("sex", 99);
        int id = user.getInt("id", 99);

        selection = "(" +
                Utility.calculateAgeBracket(dob) + " AND " +
                Utility.vocationSelection(vocation) + " AND " +
                Utility.sexSelection(sex) + " AND " +
                ConfessionContract.PersonToSinEntry.COLUMN_PERSON_ID + " = ?" +
                " AND " + ConfessionContract.PersonToSinEntry.COLUMN_COUNT + " > 0" + ")";
        selectionArgs = new String[]{String.valueOf(id)};
        getLoaderManager().initLoader(CONFESSION_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confession2, container, false);

        Button nextButton = (Button) rootView.findViewById(R.id.nextFragment);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(FRAGMENT_TAG);
            }
        });
        emptyTextView = (TextView) rootView.findViewById(R.id.emptyTextView);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewS);
        mRecyclerView.setHasFixedSize(true);

        confessionAdapter = new ConfessionAdapter();
        mRecyclerView.setAdapter(confessionAdapter); // set the adapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL ));
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        String sortOrder = ConfessionContract.SinActiveEntry._ID + " COLLATE NOCASE ASC";

        cursorLoader = new CursorLoader(getActivity(),
                ConfessionContract.SinActiveEntry.CONTENT_URI, // Uri of sins table
                CONFESSION_COLUMNS, // null projection returns all columns
                selection, // null selection returns all rows
                selectionArgs, // no selection arguments
                sortOrder); // sort order

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        confessionAdapter.swapCursor(data);
        if (confessionAdapter.getItemCount() != 0)
            emptyTextView.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        confessionAdapter.swapCursor(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
