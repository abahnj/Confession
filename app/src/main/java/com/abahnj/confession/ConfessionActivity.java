package com.abahnj.confession;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toolbar;

import java.util.Random;

public class ConfessionActivity extends Activity implements OnFragmentInteractionListener {

    private ConfessionFragment_1 confessionFragment;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confession);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null &&
                findViewById(R.id.confessionContainer) != null) {
            // create Confession Fragment
            confessionFragment = new ConfessionFragment_1();


            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getFragmentManager().beginTransaction();
            transaction.add(R.id.confessionContainer, confessionFragment);
            transaction.commit(); // display Confession Fragment
        }
    }

    @Override
    public void onFragmentInteraction(int fragmentTag) {
        switch (fragmentTag) {
            case 1:
                ConfessionFragment_2 confessionFragment2 = new ConfessionFragment_2();
                displayFragment(confessionFragment2, R.id.confessionContainer);
                break;
            case 2:
                ConfessionFragment_3 confessionFragment3 = new ConfessionFragment_3();
                displayFragment(confessionFragment3, R.id.confessionContainer);
                break;
            default:
                break;

        }
    }

    @Override
    public void onFinishButtonClicked() {

        Random rand = new Random();
        int max = 19;
        int min = 1;
        int inspirationID = rand.nextInt((max - min) + 1) + min;

        Bundle args = new Bundle();
        args.putInt(InspirationFragment.INSPIRATION_ID, inspirationID);

        DialogFragment newFragment = new InspirationFragment();
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "inspiration");

    }

    @Override
    public void onFragmentDismiss() {
        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putLong("lastConfession", System.currentTimeMillis());
        editor.apply();
        finish();
    }

    private void displayFragment(Fragment fragment, int viewID) {

        // use a FragmentTransaction to display the DetailFragment
        FragmentTransaction transaction =
                getFragmentManager().beginTransaction();
        transaction.replace(viewID, fragment);
        transaction.addToBackStack(null);
        transaction.commit(); // causes DetailFragment to display
    }
}
