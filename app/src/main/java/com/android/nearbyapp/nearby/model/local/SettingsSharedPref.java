package com.android.nearbyapp.nearby.model.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mohamed Elgendy.
 */

public class SettingsSharedPref {

    public static final String PREFERENCE_REAL_TIME_MODE_KEY = "real_time_mode";
    private static final boolean PREFERENCE_REAL_TIME_MODE_DEFAULT = true;


    private SettingsSharedPref() {
        // prevent any instance from this class
    }

    public static boolean isRealTimeMode(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(PREFERENCE_REAL_TIME_MODE_KEY, PREFERENCE_REAL_TIME_MODE_DEFAULT);
    }

    public static void setRealTimeMode(Context context , boolean isRealTime) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFERENCE_REAL_TIME_MODE_KEY, isRealTime);
        editor.apply();
    }
}
