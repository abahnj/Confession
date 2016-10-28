package com.abahnj.confession;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;
import com.github.orangegangsters.lollipin.lib.managers.AppLock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CreateUserActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int REQUEST_CODE_ENABLE = 11;
    private static SharedPreferences user;
    private static Long dob;
    private static Long lastConfession;
    private static int id;
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    private CoordinatorLayout coordinatorLayout;
    private TextView lc_tv;
    DatePickerDialog.OnDateSetListener lcL = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = new GregorianCalendar(year, month, day);
            lastConfession = c.getTimeInMillis();
            SimpleDateFormat dateInstance = new SimpleDateFormat("d'th' MMM yyyy", Locale.getDefault());
            lc_tv.setText(dateInstance.format(lastConfession));
        }
    };
    private int sex;
    private int vocation;
    View.OnClickListener BC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // hide the virtual keyboard
            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    v.getWindowToken(), 0);
            savePerson(); // save contact to the database

        }
    };
    AdapterView.OnItemSelectedListener VS = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sex_spinner:
                    sex = position;
                    break;
                case R.id.vocation_spinner:
                    vocation = position;
                    break;
                case R.id.age_spinner:
                    dob = Utility.setAge(position);
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getSharedPreferences(PREFS_NAME, 0);


        if(getIntent()!= null ){
            Intent intent = getIntent();
            String title;
            if(intent.hasExtra("settings")) {
                title = intent.getStringExtra("settings");
                getSupportActionBar().setTitle(title);
                CoordinatorLayout.LayoutParams params =
                        (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();
                params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                frameLayout.requestLayout();
            }else {
                toolbar.setVisibility(View.GONE);
            }

        }
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);




        Spinner sexSpinner = (Spinner) findViewById(R.id.sex_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sexSpinner.setAdapter(sexAdapter);
        sexSpinner.setOnItemSelectedListener(VS);
        int sexSpinnerSelection = user.getInt("sex", 99);
        if (sexSpinnerSelection != 99) {
            sexSpinner.setSelection(sexSpinnerSelection);
        }

        Spinner ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(VS);

        long ageSpinnerSelection = user.getLong("birthDate", 99);
        if (ageSpinnerSelection != 99) {
            ageSpinner.setSelection(Utility.getAge(ageSpinnerSelection));
        }

        Spinner vocationSpinner = (Spinner) findViewById(R.id.vocation_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> vocationAdapter = ArrayAdapter.createFromResource(this,
                R.array.vocation_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        vocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        vocationSpinner.setAdapter(vocationAdapter);
        vocationSpinner.setOnItemSelectedListener(VS);

        int vocationSpinnerSelection = user.getInt("vocation", 99);
        if (vocationSpinnerSelection != 99) {
            vocationSpinner.setSelection(vocationSpinnerSelection);
        }

        lc_tv = (TextView) findViewById(R.id.last_confession_button);


        // set FloatingActionButton's event listener
        Button savePersonButton = (Button) findViewById(
                R.id.save_user_button);
        savePersonButton.setOnClickListener(BC);


        lastConfession = System.currentTimeMillis();
        SimpleDateFormat dateInstance = new SimpleDateFormat("d'th' MMM yyyy", Locale.getDefault());
        lc_tv.setText(dateInstance.format(lastConfession));
    }

    // saves contact information to the database
    private void savePerson() {

        SharedPreferences.Editor editor = user.edit();

        // create ContentValues object containing contact's key-value pairs
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_BIRTHDATE, dob);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_LASTCONFESSION, lastConfession);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_MARRIED, vocation);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_SEX, sex);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_ACTOFCONTRITION, 2);

        if (id != 99) {


            int updateId = getContentResolver().update(ConfessionContract.PersonEntry.buildPersonUri(id),
                    contentValues, null, null);
            if (updateId != 0){
                editor.putInt("sex", sex);
                editor.putInt("vocation", vocation);
                editor.putInt("actOfContrition", 2);
                editor.putLong("birthDate", dob);
                editor.putLong("lastConfession", lastConfession);
                editor.apply();
                finish();
            }
        } else {
            // use Activity's ContentResolver to invoke
            // insert on the AddressBookContentProvider
            Uri newPersonUri = getContentResolver().insert(ConfessionContract.PersonEntry.CONTENT_URI, contentValues);

            if (newPersonUri != null) {
                editor.putInt("sex", sex);
                editor.putInt("vocation", vocation);
                editor.putInt("actOfContrition", 2);
                editor.putLong("birthDate", dob);
                editor.putLong("lastConfession", lastConfession);
                editor.putInt("id", Integer.valueOf(newPersonUri.getLastPathSegment()));
                editor.apply();

                Intent intent = new Intent(this, ConfessionPinActivity.class);
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                startActivityForResult(intent, REQUEST_CODE_ENABLE);
            }
        }

    }

    public void setPrefs(){
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        //  Make a new preferences editor
        SharedPreferences.Editor e = getPrefs.edit();

        //  Edit preference to make it false because we don't want this to run again
        e.putBoolean("firstStart", false);

        //  Apply changes
        e.apply();
    }


    public void showDateLC(View view) {
        new DatePickerDialog(this, lcL, year, month, day)
                .show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_ENABLE:
                setPrefs();
                Snackbar.make(coordinatorLayout, "PinCode enabled", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
