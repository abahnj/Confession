package com.abahnj.confession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("username", bundle.getString("username") );
            editor.putInt("sex",  bundle.getInt("sex")) ;
            editor.putInt("vocation", bundle.getInt("vocation"));
            editor.putInt("actOfContrition", bundle.getInt("actOfContrition"));
            editor.putLong("birthDate", bundle.getLong("birthDate"));
            editor.putLong("lastConfession", bundle.getLong("lastConfession"));
            editor.putInt("id", bundle.getInt("id"));
            editor.apply();
        }
    }

    public void examination (View view){
        Intent intent = new Intent(this, ExaminationActivity.class);
        startActivity(intent);
    }

    public void prayers(View view) {
        Intent intent = new Intent(this, PrayersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void confession(View view) {
    }
}
