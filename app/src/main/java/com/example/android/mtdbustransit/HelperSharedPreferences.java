package com.example.android.mtdbustransit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * Created by peter on 8/9/2015.
 */
public class HelperSharedPreferences {

    private static final String APP_SHARED_PREFS = HelperSharedPreferences.class.getSimpleName();
    private SharedPreferences mSharedPrefs;
    private Editor mPrefsEditor;

    public HelperSharedPreferences(Context context) {
        this.mSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.mPrefsEditor = mSharedPrefs.edit();
    }

        public static final String PREFS_LAT="prefs_lat";
        public static final String PREFS_LONG="prefs_long";
        public static final String PREFS_STOPS_LIST_CHANGESET = "stops_list_changeset";


    public void saveCurrLat( double value) {
        mPrefsEditor.putLong(PREFS_LAT, Double.doubleToRawLongBits(value));
        mPrefsEditor.commit();
    }

    public double getCurrLat() {
        return Double.longBitsToDouble(mSharedPrefs.getLong(PREFS_LAT,
                Double.doubleToRawLongBits(0)));
    }

    public void saveCurrLong(double value) {
        mPrefsEditor.putLong(PREFS_LONG, Double.doubleToRawLongBits(value));
        mPrefsEditor.commit();
    }

    public double getCurrLong() {
        return Double.longBitsToDouble(mSharedPrefs.getLong(PREFS_LONG,
                Double.doubleToRawLongBits(0)));
    }

    public void saveStopsListChangeset(String value) {
        mPrefsEditor.putString(PREFS_STOPS_LIST_CHANGESET, value);
        mPrefsEditor.commit();
    }

    public String getStopsListChangeset() {
        return mSharedPrefs.getString(PREFS_STOPS_LIST_CHANGESET, "");
    }

}
