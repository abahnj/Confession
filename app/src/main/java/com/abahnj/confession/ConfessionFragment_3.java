package com.abahnj.confession;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

import static android.util.TypedValue.applyDimension;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConfessionFragment_3 extends Fragment {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String[] CONTRITION_COLUMNS = {
            ConfessionContract.PrayersEntry.COLUMN_PRAYERTEXT
    };

    private OnFragmentInteractionListener mListener;
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

        getActivity().findViewById(R.id.linearLayout2).setVisibility(View.GONE);
        AppBarLayout appbar = (AppBarLayout) getActivity().findViewById(R.id.appbar1);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        params.height = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, getResources().getDisplayMetrics());
        appbar.setLayoutParams(params);

        finishConfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFinishButtonClicked();
            }
        });
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
