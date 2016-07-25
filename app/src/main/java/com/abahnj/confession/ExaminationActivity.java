package com.abahnj.confession;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ExaminationActivity extends AppCompatActivity implements CommandmentFragment.CommandmentFragmentListener, ExaminationFragment.OnFragmentInteractionListener{

    public static final String COMMANDMENT_URI = "commandment_uri";
    private CommandmentFragment commandmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null &&
                findViewById(R.id.examinationContainer) != null) {
            // create Examination Fragment
            commandmentFragment = new CommandmentFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.examinationContainer, commandmentFragment);
            transaction.commit(); // display Examination Fragment
        }

    }

    @Override
    public void onCommandmentSelected(Uri commandmentUri) {
        Toast.makeText(this, commandmentUri.toString(), Toast.LENGTH_SHORT).show();
        if (findViewById(R.id.examinationContainer) != null) // phone
            displayCommandment(commandmentUri, R.id.examinationContainer);
        else { // tablet
            // removes top of back stack
            getSupportFragmentManager().popBackStack();

            // displayCommandment(commandmentUri, R.id.rightPaneContainer);
        }

    }

    private void displayCommandment(Uri commandmentUri, int viewID){
        ExaminationFragment examinationFragment = new ExaminationFragment();

        int commandmentID = Integer.valueOf(commandmentUri.getLastPathSegment());
        // specify commandment's Uri as an argument to the ExaminationFragment
        Bundle arguments = new Bundle();
        arguments.putInt(COMMANDMENT_URI, commandmentID);
        examinationFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailFragment
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, examinationFragment);
        transaction.addToBackStack(null);
        transaction.commit(); // causes DetailFragment to display
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void switchFragment(View view) {


    }
}
