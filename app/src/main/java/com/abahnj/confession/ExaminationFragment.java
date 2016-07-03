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

import com.abahnj.confession.data.ConfessionContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExaminationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExaminationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExaminationFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final int EXAMINATION_LOADER = 2;// identifies Loader
    public static final String PREFS_NAME = "MyPrefsFile";
    private String selection;
    private String[] selectionArgs;


    // TODO: Rename and change types of parameters
    private int commandmentID;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ExaminationAdapter examinationAdapter;
    private static final String[] EXAMINATION_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            ConfessionContract.SinEntry.TABLE_NAME + "." + ConfessionContract.SinEntry._ID,
            ConfessionContract.SinEntry.TABLE_NAME + "." + ConfessionContract.SinEntry.COLUMN_DESCRIPTION,
            ConfessionContract.PersonToSinEntry.TABLE_NAME + "." + ConfessionContract.PersonToSinEntry.COLUMN_PERSON_ID,
            ConfessionContract.PersonToSinEntry.TABLE_NAME + "." + ConfessionContract.PersonToSinEntry.COLUMN_COUNT

    };
    public ExaminationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExaminationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExaminationFragment newInstance(String param1, String param2) {
        ExaminationFragment fragment = new ExaminationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences user = getContext().getSharedPreferences(PREFS_NAME, 0);
        int vocation = user.getInt("vocation", 99);
        long dob = user.getLong("birthDate", 99);
        int sex = user.getInt("sex", 99);
        int id = user.getInt("id", 99);

        if (getArguments() != null) {
            commandmentID = getArguments().getInt(ExaminationActivity.COMMANDMENT_URI);
        }
        selection = "(" +ConfessionContract.SinEntry.COLUMN_COMMANDMENT_ID + " = ? AND " +
                Utility.calculateAgeBracket(dob) + " AND " +
                Utility.vocationSelection(vocation)+ " AND " +
                Utility.sexSelection(sex) + " AND " +
                ConfessionContract.PersonToSinEntry.COLUMN_PERSON_ID + " = ?" + ")"
        + " OR " +"(" +ConfessionContract.SinEntry.COLUMN_COMMANDMENT_ID + " = ? AND " +
                Utility.calculateAgeBracket(dob) + " AND " +
                Utility.vocationSelection(vocation)+ " AND " +
                Utility.sexSelection(sex) + " AND " +
                ConfessionContract.PersonToSinEntry.COLUMN_PERSON_ID + " is NULL" + ")";

        selectionArgs = new String[]{String.valueOf(commandmentID), String.valueOf(id), String.valueOf(commandmentID) };
        getLoaderManager().initLoader(EXAMINATION_LOADER, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_examination, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewE);

        // recyclerView should display items in a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // create recyclerView's adapter and item click listener
        examinationAdapter = new ExaminationAdapter(new ExaminationAdapter.ExaminationClickListener() {
            @Override
            public void onClick(Uri examinationUri, int position) {

            }
        });

        recyclerView.setAdapter(examinationAdapter); // set the adapter
        return rootView;    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        String sortOrder = ConfessionContract.SinEntry._ID + " COLLATE NOCASE ASC";

        cursorLoader = new CursorLoader(getActivity(),
                ConfessionContract.SinEntry.CONTENT_URI, // Uri of sins table
                EXAMINATION_COLUMNS, // null projection returns all columns
                selection, // null selection returns all rows
                selectionArgs, // no selection arguments
                sortOrder); // sort order

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        examinationAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        examinationAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
