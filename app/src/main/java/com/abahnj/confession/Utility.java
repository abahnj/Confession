package com.abahnj.confession;

import android.content.Context;

import com.abahnj.confession.data.ConfessionContract.SinEntry;

import java.util.Calendar;

/**
 * Created by abahnj on 7/3/2016.
 */
public class Utility {

    private static final int SUCCESS_RETURN_CODE = 1;

    public static String calculateAgeBracket(Long birthDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -13);
        long teen_limit = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -5);
        long adult_limit = calendar.getTimeInMillis();

        if (birthDate > teen_limit) {
            return SinEntry.COLUMN_CHILD + " = 1";
        } else if(birthDate > adult_limit) {
            return SinEntry.COLUMN_TEEN + " = 1";
        } else {
            return SinEntry.COLUMN_ADULT + " = 1";
        }
    }

    public static String vocationSelection (int vocation){

        String vocationSelection;
        switch (vocation){
            case 0:
                vocationSelection = SinEntry.COLUMN_SINGLE + " = 1";
                break;
            case 1:
                vocationSelection = SinEntry.COLUMN_MARRIED + " = 1";
                break;
            case 2:
                vocationSelection = SinEntry.COLUMN_PRIEST + " = 1";
                break;
            case 3:
                vocationSelection = SinEntry.COLUMN_RELIGIOUS + " = 1";
                break;
            default:
                throw new UnsupportedOperationException("Unknown vocation: " + vocation);        }
        return vocationSelection;
        }

    public static String sexSelection(int sex) {
        String sexSelection;
        switch (sex){
            case 0:
                sexSelection = SinEntry.COLUMN_MALE + " = 1";
                break;
            case 1:
                sexSelection = SinEntry.COLUMN_FEMALE + " = 1";
                break;
            default:
                throw new UnsupportedOperationException("Unknown sex: " + sex);        }
        return sexSelection;
    }

    public static String lastConfession(Context context, long lastConfession) {
        String timeSince = null;
        long currentTime = System.currentTimeMillis();
        long timeSinceLC = currentTime - lastConfession;
        int days = ((int) (timeSinceLC / 86400000)) + SUCCESS_RETURN_CODE;
        int weeks = days / 7;
        int months = days / 31;
        int years = days / 365;
        if (years > SUCCESS_RETURN_CODE) {
            timeSince = String.valueOf(String.valueOf(years)) + " years";
        } else if (months > SUCCESS_RETURN_CODE) {
            timeSince = String.valueOf(String.valueOf(months)) + " months";
        } else if (weeks > SUCCESS_RETURN_CODE) {
            timeSince = String.valueOf(String.valueOf(weeks)) + " weeks";
        } else if (days > SUCCESS_RETURN_CODE) {
            timeSince = String.valueOf(String.valueOf(days)) + " days";
        }
        if (timeSince != null) {
            return (String.format(context.getString(R.string.time_since_last), timeSince));
        } else {
            return context.getString(R.string.null_time_since_last);
        }
    }

}



