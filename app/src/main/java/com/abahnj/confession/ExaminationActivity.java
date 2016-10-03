package com.abahnj.confession;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.abahnj.confession.data.ConfessionContract;
import com.bumptech.glide.Glide;
import com.github.orangegangsters.lollipin.lib.PinCompatActivity;

public class ExaminationActivity extends PinCompatActivity implements CommandmentFragment.CommandmentFragmentListener, ExaminationFragment.OnFragmentInteractionListener {

    public static final String COMMANDMENT_URI = "commandment_uri";
    private static final String PREFS_NAME = "MyPrefsFile";
    private int vocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
        vocation = user.getInt("vocation", 99);

        if (savedInstanceState == null &&
                findViewById(R.id.examinationContainer) != null) {
            // create Examination Fragment
            CommandmentFragment commandmentFragment = new CommandmentFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.examinationContainer, commandmentFragment);
            transaction.commit(); // display Examination Fragment
        }
        loadBackdrop();
    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.examination1).centerCrop().into(imageView);
    }

    @Override
    public void onCommandmentSelected(Uri commandmentUri, Boolean addToBackStack) {
        Toast.makeText(this, commandmentUri.toString(), Toast.LENGTH_SHORT).show();
        if (findViewById(R.id.examinationContainer) != null) // phone
            displayCommandment(commandmentUri, R.id.examinationContainer, addToBackStack);
        else { // tablet
            // removes top of back stack
            getSupportFragmentManager().popBackStack();

            // displayCommandment(commandmentUri, R.id.rightPaneContainer);
        }

    }

    private void displayCommandment(Uri commandmentUri, int viewID, Boolean addToBackStack) {

        ExaminationFragment examinationFragment = new ExaminationFragment();

        int commandmentID = Integer.valueOf(commandmentUri.getLastPathSegment());
        // specify commandment's Uri as an argument to the ExaminationFragment
        Bundle arguments = new Bundle();
        arguments.putInt(COMMANDMENT_URI, commandmentID);
        examinationFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailFragment
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("relevant");
        if (!addToBackStack)
            getSupportFragmentManager().popBackStack("relevant", FragmentManager.POP_BACK_STACK_INCLUSIVE);

        transaction.replace(viewID, examinationFragment);
        transaction.commit();
        // causes DetailFragment to display
    }

    @Override
    public void onFragmentInteraction(int num, int commandmentID) {
        int newFragment;
        if (vocation <= 1) {
            switch (commandmentID) {
                case (1):
                    if (num >= 1)
                        newFragment = (commandmentID % 10) + num;
                    else
                        newFragment = 10;
                    break;
                case (10):
                    if (num < 1)
                        newFragment = 9;
                    else
                        newFragment = (commandmentID % 10) + num;
                    break;
                default:
                    newFragment = (commandmentID % 10) + num;
                    break;
            }
        } else {
            switch (commandmentID) {
                case (15):
                    if (num >= 1)
                        newFragment = 11;
                    else
                        newFragment = (commandmentID % 10) + num + 10;
                    break;
                default:
                    newFragment = (commandmentID % 10) + num + 10;
                    break;
            }
        }
        Uri commandmentUri = ConfessionContract.CommandmentEntry.buildCommandmentUri((long) newFragment);
        onCommandmentSelected(commandmentUri, false);
    }
}
