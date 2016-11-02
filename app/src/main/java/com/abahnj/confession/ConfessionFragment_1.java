package com.abahnj.confession;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.util.TypedValue.applyDimension;

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

        getActivity().findViewById(R.id.linearLayout2).setVisibility(View.VISIBLE);
        AppBarLayout appbar = (AppBarLayout) getActivity().findViewById(R.id.appbar1);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        params.height = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 192, getResources().getDisplayMetrics());
        appbar.setLayoutParams(params);

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
