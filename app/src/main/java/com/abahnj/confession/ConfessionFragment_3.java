package com.abahnj.confession;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConfessionFragment_3 extends Fragment {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int FRAGMENT_TAG = 3;
    private static final String[] CONTRITION_COLUMNS = {
            ConfessionContract.PrayersEntry.COLUMN_PRAYERTEXT
    };

    public ConfessionFragment_3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confession3, container, false);
        Button finishConfession = (Button) rootView.findViewById(R.id.finishConfession);
        TextView actOfContrition = (TextView) rootView.findViewById(R.id.act_of_contrition);
        SharedPreferences user = getActivity().getSharedPreferences(PREFS_NAME, 0);
        int contrition = user.getInt("actOfContrition", 6);
        actOfContrition.setText(updateTextView(contrition));


        return rootView;
    }

    private String updateTextView(int actOfContrition) {
        String contrition = null;

        Cursor cursor = getActivity().getContentResolver().query(ConfessionContract.PrayersEntry.CONTENT_URI,
                CONTRITION_COLUMNS,
                ConfessionContract.PrayersEntry._ID + " = " + actOfContrition,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            contrition = cursor.getString(cursor.getColumnIndex(ConfessionContract.PrayersEntry.COLUMN_PRAYERTEXT));
        }

        if (cursor != null) {
            cursor.close();
        }
        return contrition;
    }
}
