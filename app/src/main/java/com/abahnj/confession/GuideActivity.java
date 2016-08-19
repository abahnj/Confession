package com.abahnj.confession;

import android.os.Bundle;
import android.widget.Toolbar;

import com.github.orangegangsters.lollipin.lib.PinActivity;

public class GuideActivity extends PinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
