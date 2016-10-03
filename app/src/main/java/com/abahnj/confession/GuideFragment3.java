package com.abahnj.confession;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;


public class GuideFragment3 extends Fragment {

    private static final String[] GUIDE_COLUMNS = {
            ConfessionContract.GuideEntry.COLUMN_GUIDE_TITLE,
            ConfessionContract.GuideEntry.COLUMN_GUIDE_TEXT
    };
    private String[] detailID;
    private static final String DETAIL_ID ="detail_id" ;
    private String guideTitle;
    private String guideBody;

    public GuideFragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int categoryId = getArguments().getInt(DETAIL_ID);
            detailID = new String[]{String.valueOf(categoryId)};
            fetchStrings();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guide_3, container, false);
        TextView guideTextViewHeading = (TextView) rootView.findViewById(R.id.FGD_HeadingTextView);
        TextView guideTextView = (TextView) rootView.findViewById(R.id.FGD_TextView);
        guideTextViewHeading.setText(guideTitle);
        guideTextView.setText(guideBody);
        // Inflate the layout for this fragment
        return rootView;
    }


    private void fetchStrings(){

        Cursor cursor = getActivity().getContentResolver().query(ConfessionContract.GuideEntry.CONTENT_URI,
                GUIDE_COLUMNS,
                ConfessionContract.GuideEntry._ID + " = ?",
                detailID,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            guideTitle = cursor.getString(cursor.getColumnIndex(ConfessionContract.GuideEntry.COLUMN_GUIDE_TITLE));
            guideBody = cursor.getString(cursor.getColumnIndex(ConfessionContract.GuideEntry.COLUMN_GUIDE_TEXT));
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
