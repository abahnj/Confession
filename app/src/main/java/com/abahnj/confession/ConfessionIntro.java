package com.abahnj.confession;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by abahnj on 8/11/2016.
 */
public class ConfessionIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Secured", "To keep away from prying eyes", R.drawable.intro1, Color.TRANSPARENT));
        addSlide(AppIntroFragment.newInstance("Simple", "Beautiful and fluid UI ", R.drawable.intro2, Color.parseColor("#5C6BC0")));
        addSlide(AppIntroFragment.newInstance("Catholic", "Fully in line with the Churches teachings", R.drawable.intro3, Color.parseColor("#4CAF50")));
        addSlide(AppIntroFragment.newInstance("Guide", "Step by step guide to a Catholic Confession", R.drawable.intro4, Color.parseColor("#00BCD4")));

        showSkipButton(false);
        //setSeparatorColor(Color.parseColor("#2196F3"));

    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }
}
