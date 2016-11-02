package com.abahnj.confession;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.abahnj.confession.data.ConfessionContract;
import com.github.orangegangsters.lollipin.lib.PinCompatActivity;

import java.util.Random;

import static android.util.TypedValue.applyDimension;

public class ConfessionActivity extends PinCompatActivity implements OnFragmentInteractionListener {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confession);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button nextButton = (Button) findViewById(R.id.nextFragment);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentInteraction(1);
            }
        });


        if (savedInstanceState == null &&
                findViewById(R.id.confessionContainer) != null) {
            // create Confession Fragment
            ConfessionFragment_1 confessionFragment = new ConfessionFragment_1();


            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.confessionContainer, confessionFragment);
            transaction.commit(); // display Confession Fragment
        }
    }

    @Override
    public void onFragmentInteraction(int fragmentTag) {
        switch (fragmentTag) {
            case 1:
                AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar1);
                ConfessionFragment_2 confessionFragment2 = new ConfessionFragment_2();
                displayFragment(confessionFragment2, R.id.confessionContainer);

                Animation ani = new ResizeHeightAnimation(appbar, (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, getResources().getDisplayMetrics())/* target layout height */);
                ani.setDuration(500/* animation time */);
                appbar.startAnimation(ani);

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

        getContentResolver().delete(ConfessionContract.PersonToSinEntry.CONTENT_URI, null, null);
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
                getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, fragment);
        transaction.addToBackStack(null);
        transaction.commit(); // causes DetailFragment to display
    }
}
