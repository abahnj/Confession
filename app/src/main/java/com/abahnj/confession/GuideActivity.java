package com.abahnj.confession;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
        loadBackdrop();
    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.guide).centerCrop().into(imageView);
    }
    @Override
    public void onFragmentInteraction(int guideId) {
        GuideFragment2 fragment = new GuideFragment2();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, guideId);
        fragment.setArguments(args);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guideContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
