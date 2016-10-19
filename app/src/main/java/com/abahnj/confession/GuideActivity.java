package com.abahnj.confession;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.github.orangegangsters.lollipin.lib.PinCompatActivity;

public class GuideActivity extends PinCompatActivity implements GuideFragment.OnFragmentInteractionListener, GuideFragment2.OnDetailClickListener {

    private static final String CATEGORY_ID ="guide_id" ;
    private static final String DETAIL_ID ="detail_id" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null &&
                findViewById(R.id.guideContainer) != null) {
            // create Examination Fragment
            GuideFragment guideFragment = new GuideFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.guideContainer, guideFragment);
            transaction.commit(); // display Examination Fragment
        }
    }

    @Override
    public void onFragmentInteraction(int guideId) {
        Intent intent = new Intent(this, GuideActivity2.class);
        intent.putExtra("guideId", guideId);
        startActivity(intent);

        /*GuideFragment2 fragment = new GuideFragment2();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, guideId);
        fragment.setArguments(args);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guideContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        loadBackdrop(guideId);*/
    }


    @Override
    public void showDetailView(int guideId) {
        GuideFragment3 fragment = new GuideFragment3();
        Bundle args = new Bundle();
        args.putInt(DETAIL_ID, guideId);
        fragment.setArguments(args);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guideContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
