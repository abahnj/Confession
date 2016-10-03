package com.abahnj.confession;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abahnj.confession.data.ConfessionContract;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PrayersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int PRAYERS_LOADER = 1;// identifies Loader
    private static final String[] PRAYERS_COLUMNS = {
            ConfessionContract.PrayersEntry._ID,
            ConfessionContract.PrayersEntry.COLUMN_PRAYERNAME
    };
    private PrayersAdapter prayersAdapter;
    private RecyclerView mRecyclerView;
    private PrayersFragmentListener mListener;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;

    public PrayersFragment() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PrayersFragmentListener) {
            mListener = (PrayersFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PrayersFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prayers, container, false);

        getLoaderManager().initLoader(PRAYERS_LOADER, null, this);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewP);
        mRecyclerView.setHasFixedSize(true);

        prayersAdapter = new PrayersAdapter(new PrayersAdapter.PrayersClickListener() {
            @Override
            public void onClick(Uri prayersUri, int position) {
                mListener.onPrayersSelected(prayersUri);
            }
        });

        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Act of Contrition"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(7, "Traditional Prayers"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getContext(), R.layout.section, R.id.section_text, prayersAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //mRecyclerView.setAdapter(mSectionedAdapter);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //getLoaderManager().initLoader(PRAYERS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        AppBarLayout toolbar = (AppBarLayout) getActivity().findViewById(R.id.appbar);  // or however you need to do it for your code
        toolbar.setExpanded(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        String sortOrder = ConfessionContract.PrayersEntry._ID + " COLLATE NOCASE ASC";

        cursorLoader = new CursorLoader(getActivity(),
                ConfessionContract.PrayersEntry.CONTENT_URI, // Uri of sins table
                PRAYERS_COLUMNS, // null projection returns all columns
                null, // null selection returns all rows
                null, // no selection arguments
                sortOrder); // sort order

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        prayersAdapter.swapCursor(data);
        mRecyclerView.setAdapter(mSectionedAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        prayersAdapter.swapCursor(null);
    }

    // callback method implemented by ExaminationActivity
    public interface PrayersFragmentListener {
        // called when contact selected
        void onPrayersSelected(Uri prayersUri);
    }
}
