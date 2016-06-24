package com.abahnj.confession;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abahnj.confession.data.ConfessionContract.CommandmentEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExaminationActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private ExaminationAdapter examinationAdapter;
    private static final int EXAMINATION_LOADER = 1;// identifies Loader
    public ExaminationActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(EXAMINATION_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_examination, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewE);

        // recyclerView should display items in a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // create recyclerView's adapter and item click listener
        examinationAdapter = new ExaminationAdapter();

        recyclerView.setAdapter(examinationAdapter); // set the adapter
        return rootView;

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    // create an appropriate CursorLoader based on the id argument;
        // only one Loader in this fragment, so the switch is unnecessary
        String sortOrder = CommandmentEntry.COLUMN_NUMBER + " COLLATE NOCASE ASC";

        return new CursorLoader(getActivity(),
                CommandmentEntry.CONTENT_URI, // Uri of contacts table
                null, // null projection returns all columns
                null, // null selection returns all rows
                null, // no selection arguments
                sortOrder); // sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    examinationAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    examinationAdapter.swapCursor(null);
    }
}
