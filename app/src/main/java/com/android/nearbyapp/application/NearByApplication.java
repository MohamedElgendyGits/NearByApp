package com.android.nearbyapp.application;

import android.app.Application;

import com.patloew.rxlocation.RxLocation;

/**
 * Created by Mohamed Elgendy
 */

public class NearByApplication extends Application {

    private static NearByApplication instance;
    private static RxLocation rxLocation;
    private static boolean isActivityVisible;

    public static NearByApplication getInstance() {
        if (instance == null){
            throw new IllegalStateException("Something went horribly wrong!!, no application attached!");
        }
        return instance;
    }

    public static RxLocation getRxLocationInstance() {
        if (rxLocation == null){
            throw new IllegalStateException("Something went horribly wrong!!, no application attached!");
        }
        return rxLocation;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        rxLocation = new RxLocation(this);
    }

    public static boolean isActivityVisible() {
        return isActivityVisible;
    }

    public static void activityResumed() {
        isActivityVisible = true;
    }

    public static void activityStopped() {
        isActivityVisible = false;

    }
}
