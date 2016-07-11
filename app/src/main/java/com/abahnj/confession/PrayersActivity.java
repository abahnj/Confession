package com.abahnj.confession;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class PrayersActivity extends AppCompatActivity implements PrayersFragment.PrayersFragmentListener {

    private static final String PRAYERS_URI = "prayers_uri";
    private PrayersFragment prayersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null &&
                findViewById(R.id.prayersContainer) != null) {
            // create Examination Fragment
            prayersFragment = new PrayersFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.prayersContainer, prayersFragment);
            transaction.commit(); // display Examination Fragment
        }
    }

    @Override
    public void onPrayersSelected(Uri prayersUri) {
        Toast.makeText(this, prayersUri.toString(), Toast.LENGTH_SHORT).show();
        if (findViewById(R.id.prayersContainer) != null) // phone
            displayPrayers(prayersUri, R.id.prayersContainer);
        else { // tablet
            // removes top of back stack
            getSupportFragmentManager().popBackStack();

            // displayCommandment(commandmentUri, R.id.rightPaneContainer);
        }
    }

    private void displayPrayers(Uri prayersUri, int viewID) {
        PrayersDetailFragment prayerDetailFragment = new PrayersDetailFragment();

        int prayerID = Integer.valueOf(prayersUri.getLastPathSegment());
        // specify commandment's Uri as an argument to the ExaminationFragment
        Bundle arguments = new Bundle();
        arguments.putInt(PRAYERS_URI, prayerID);
        prayerDetailFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailFragment
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, prayerDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit(); // causes DetailFragment to display
    }


}
