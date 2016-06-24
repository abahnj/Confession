package com.abahnj.confession;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static Uri mUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mUri = intent.getData();
    }

    public void examination (View view){
        Intent intent = new Intent(this, ExaminationActivity.class);
        startActivity(intent);
    }

    public void prayers(View view) {
        Toast.makeText(this, mUri.toString(), Toast.LENGTH_LONG).show();
    }

    public void confession(View view) {
    }
}
