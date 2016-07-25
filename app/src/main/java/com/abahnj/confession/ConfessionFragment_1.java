package com.abahnj.confession;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConfessionFragment_1 extends Fragment {

    private static final int FRAGMENT_TAG = 1;
    private OnFragmentInteractionListener mListener;

    public ConfessionFragment_1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confession, container, false);
        TextView lcTextView = (TextView) rootView.findViewById(R.id.time_since_last);
        SharedPreferences user = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        long lastConfession = user.getLong("lastConfession", 99);
        Button nextButton = (Button) rootView.findViewById(R.id.nextFragment);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(FRAGMENT_TAG);
            }
        });
        lcTextView.setText(Utility.lastConfession(getActivity(), lastConfession));

        return rootView;
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
