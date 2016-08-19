package com.abahnj.confession;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrayersDetailFragment extends Fragment {

    private static final String PRAYERS_URI = "prayers_uri";
    private static final String[] PRAYERS_COLUMNS = {
            ConfessionContract.PrayersEntry.COLUMN_PRAYERTEXT,
            ConfessionContract.PrayersEntry.COLUMN_PRAYERNAME
    };
    private int prayersID;

    public PrayersDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prayers_detail, container, false);

        TextView prayerTextViewHeading = (TextView) rootView.findViewById(R.id.FPD_HeadingTextView);
        TextView prayerTextView = (TextView) rootView.findViewById(R.id.FPD_TextView);
        // Inflate the layout for this fragment
        Cursor cursor = getContext().getContentResolver().query(ConfessionContract.PrayersEntry.CONTENT_URI,
                PRAYERS_COLUMNS,
                ConfessionContract.PrayersEntry._ID + " = " + prayersID,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String prayerText = cursor.getString(cursor.getColumnIndex(ConfessionContract.PrayersEntry.COLUMN_PRAYERTEXT));
            String prayerTextHeading = cursor.getString(cursor.getColumnIndex(ConfessionContract.PrayersEntry.COLUMN_PRAYERNAME));
            cursor.close();
            prayerTextView.setText(prayerText);
            prayerTextViewHeading.setText(prayerTextHeading);

        }

        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prayersID = getArguments().getInt(PrayersActivity.PRAYERS_URI);
        }

    }

}
