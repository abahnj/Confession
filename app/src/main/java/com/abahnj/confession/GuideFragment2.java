package com.abahnj.confession;


import android.content.Context;
import android.database.Cursor;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment2 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String CATEGORY_ID ="guide_id" ;
    private static final int GUIDE_LOADER = 1;
    private static final String[] GUIDE_COLUMNS = {
            ConfessionContract.GuideEntry._ID,
            ConfessionContract.GuideEntry.COLUMN_GUIDE_ID,
            ConfessionContract.GuideEntry.COLUMN_HEADER_ID,
            ConfessionContract.GuideEntry.COLUMN_GUIDE_TITLE
    };
    private OnDetailClickListener clickListener;
    private GuideAdapter guideAdapter;
    private String[] selectionArgs;
    private RecyclerView mRecyclerView;
    public GuideFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int categoryId = getArguments().getInt(CATEGORY_ID);
            selectionArgs = new String[]{String.valueOf(categoryId)};
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        AppBarLayout toolbar = (AppBarLayout) getActivity().findViewById(R.id.appbar);  // or however you need to do it for your code
        toolbar.setExpanded(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getLoaderManager().initLoader(GUIDE_LOADER, null, this);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_guide_2, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewG);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        guideAdapter = new GuideAdapter(new GuideAdapter.GuideClickListener() {
            @Override
            public void onClick(int position) {
                clickListener.showDetailView(position);
            }
        });
        mRecyclerView.setAdapter(guideAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        // Set title bar
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailClickListener) {
            clickListener = (OnDetailClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        clickListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        String sortOrder = ConfessionContract.GuideEntry._ID + " COLLATE NOCASE ASC";

        cursorLoader = new CursorLoader(getActivity(),
                ConfessionContract.GuideEntry.CONTENT_URI, // Uri of sins table
                GUIDE_COLUMNS, // null projection returns all columns
                ConfessionContract.GuideEntry.COLUMN_HEADER_ID + "= ?", // null selection returns all rows
                selectionArgs, // no selection arguments
                sortOrder); // sort order

        return cursorLoader;    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        guideAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        guideAdapter.swapCursor(null);
    }

    public interface OnDetailClickListener {
        // TODO: Update argument type and name
        void showDetailView(int guideId);
    }

}
