package com.abahnj.confession;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
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

import com.abahnj.confession.data.ConfessionContract.CommandmentEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommandmentFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int COMMANDMENT_LOADER = 1;// identifies Loader
    private CommandmentAdapter commandmentAdapter;
    private CommandmentFragmentListener mListener;

    public CommandmentFragment() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommandmentFragmentListener) {
            mListener = (CommandmentFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CommandmentFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(COMMANDMENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_activity_examination);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commandments, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewC);

        // recyclerView should display items in a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // create recyclerView's adapter and item click listener
        commandmentAdapter = new CommandmentAdapter(new CommandmentAdapter.CommandmentClickListener() {
            @Override
            public void onClick(Uri examinationUri, int position) {
                mListener.onCommandmentSelected(examinationUri);
            }
        });

        recyclerView.setAdapter(commandmentAdapter); // set the adapter
        return rootView;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // create an appropriate CursorLoader based on the id argument;
        // only one Loader in this fragment, so the switch is unnecessary
        CursorLoader cursorLoader = null;
        String sortOrder = CommandmentEntry.COLUMN_NUMBER + " COLLATE NOCASE ASC";

        SharedPreferences user = getActivity().getSharedPreferences(PREFS_NAME, 0);
        int vocation = user.getInt("vocation", 99);
        if (vocation <= 1) {
            cursorLoader = new CursorLoader(getActivity(),
                    CommandmentEntry.CONTENT_URI, // Uri of contacts table
                    null, // null projection returns all columns
                    CommandmentEntry.COLUMN_NUMBER + "!=" + "0", // null selection returns all rows
                    null, // no selection arguments
                    sortOrder); // sort order
        } else if (vocation > 1 && vocation <= 3) {
            cursorLoader = new CursorLoader(getActivity(),
                    CommandmentEntry.CONTENT_URI, // Uri of contacts table
                    null, // null projection returns all columns
                    CommandmentEntry.COLUMN_NUMBER + "=" + "0", // null selection returns all rows
                    null, // no selection arguments
                    sortOrder); // sort order
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        commandmentAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        commandmentAdapter.swapCursor(null);
    }

    // callback method implemented by ExaminationActivity
    public interface CommandmentFragmentListener {
        // called when contact selected
        void onCommandmentSelected(Uri commandmentUri);

    }


}
