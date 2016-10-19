package com.abahnj.confession;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.orangegangsters.lollipin.lib.PinCompatActivity;

public class GuideActivity2 extends PinCompatActivity implements GuideFragment2.OnDetailClickListener {

    private static final String CATEGORY_ID = "guide_id";
    private static final String DETAIL_ID = "detail_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int guideId = getIntent().getIntExtra("guideId", 99);
        getSupportActionBar().setTitle(setFragmentTitle(guideId));
        loadBackdrop(guideId);

        if (savedInstanceState == null &&
                findViewById(R.id.guideContainer2) != null) {
            // create Examination Fragment
            GuideFragment2 guideFragment = new GuideFragment2();
            Bundle args = new Bundle();
            args.putInt(CATEGORY_ID, guideId);
            guideFragment.setArguments(args);

            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.guideContainer2, guideFragment);
            transaction.commit(); // display Guide Fragment
        }
    }

    private void loadBackdrop(int guideId) {
        int rId;
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        switch (guideId) {
            case 1:
                rId = R.drawable.faq;
                break;
            case 2:
                rId = R.drawable.as_said_by_pope1;
                break;
            case 3:
                rId = R.drawable.fulton_sheen;
                break;
            case 4:
                rId = R.drawable.ccc2;
                break;
            default:
                rId = R.drawable.how_to_make_a_good_confession;
                break;
        }
        Glide.with(this).load(rId).centerCrop().into(imageView);
    }

    @NonNull
    private String setFragmentTitle(int guideId) {
        String Title;
        switch (guideId) {
            case 1:
                Title = getResources().getString(R.string.faq);
                break;
            case 2:
                Title = getResources().getString(R.string.as_said_by_popes);
                break;
            case 3:
                Title = getResources().getString(R.string.extracts_fulton_sheen);
                break;
            case 4:
                Title = getResources().getString(R.string.catechism_of_the_catholic_church);
                break;
            default:
                Title = getResources().getString(R.string.how_to_make_a_good_confession);
                break;

        }
        return Title;
    }


    @Override
    public void showDetailView(int guideId) {
        GuideFragment3 fragment = new GuideFragment3();
        Bundle args = new Bundle();
        args.putInt(DETAIL_ID, guideId);
        fragment.setArguments(args);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guideContainer2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
