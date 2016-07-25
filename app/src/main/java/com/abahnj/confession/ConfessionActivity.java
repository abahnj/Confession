package com.abahnj.confession;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toolbar;

public class ConfessionActivity extends Activity implements OnFragmentInteractionListener {

    private ConfessionFragment_1 confessionFragment;


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
            case 3:
                ConfessionFragment_4 confessionFragment4 = new ConfessionFragment_4();
                displayFragment(confessionFragment4, R.id.confessionContainer);
                break;
            case 4:

                break;
            default:
                break;

        }
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
