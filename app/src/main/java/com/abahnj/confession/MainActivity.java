package com.abahnj.confession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.orangegangsters.lollipin.lib.PinCompatActivity;

public class MainActivity extends PinCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, ConfessionIntro.class);
                    startActivity(i);


                }
            }
        });

        // Start the thread
        t.start();

        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            user.getString("username", "username" );
            user.getInt("sex", 99) ;
            user.getInt("vocation", 99);
            user.getInt ("actOfContrition", 99);
            user.getLong("birthDate", 99);
            user.getLong("lastConfession", 99);
            user.getInt("id", 99);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    private void showSettings() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        intent.putExtra("settings", "Settings");
        startActivity(intent);
    }

    private void resetApp() {
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        //  Make a new preferences editor
        SharedPreferences.Editor e = getPrefs.edit();

        //  Edit preference to make it false because we don't want this to run again
        e.putBoolean("firstStart", true);
        e.apply();
    }

    public void examination (View view){
        Intent intent = new Intent(this, ExaminationActivity.class);
        startActivity(intent);
    }

    public void prayers(View view) {
        Intent intent = new Intent(this, PrayersActivity.class);
        startActivity(intent);
    }

    public void confession(View view) {
        Intent intent = new Intent(this, ConfessionActivity.class);
        startActivity(intent);
    }


    public void guide(View view) {
        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
        startActivity(intent);
    }


}
