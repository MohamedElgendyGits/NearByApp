package com.android.nearbyapp.main.presenter;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.nearbyapp.application.NearByApplication;
import com.android.nearbyapp.application.NearByConstants;
import com.android.nearbyapp.main.MainActivity;
import com.android.nearbyapp.main.view.MainView;
import com.android.nearbyapp.nearby.model.local.SettingsSharedPref;
import com.android.nearbyapp.utils.PermissionUtils;

/**
 * Created by Mohamed Elgendy.
 */

public class MainPresenterImpl implements MainPresenter {

    private final String TAG = MainPresenterImpl.class.getSimpleName();
    MainView mainView;

    public MainPresenterImpl(MainView mainView, Activity activity) {
        this.mainView = mainView;
        checkAccessLocationPermission(activity);
    }

    private void checkAccessLocationPermission(Activity activity) {

        if (PermissionUtils.useRunTimePermissions()) {
            askForRuntimePermission(activity, Manifest.permission.ACCESS_FINE_LOCATION,
                    NearByConstants.REQUEST_ACCESS_FINE_LOCATION_PERMISSION_CODE);
        } else {
            mainView.showNearByFragment();
        }
    }

    @Override
    public void askForRuntimePermission(Activity activity, String permission, int requestCode) {

        if (!PermissionUtils.hasPermission(activity, permission)) {
            if (PermissionUtils.shouldShowRational(activity, permission)) {
                //user has denied the permission before and asking the permission again
                PermissionUtils.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                PermissionUtils.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        } else {
            // permission is already granted
            mainView.showNearByFragment();
        }
    }

    @Override
    public void validateRuntimePermissionResult(Activity activity, String permission, int requestCode) {

        if (PermissionUtils.hasPermission(activity, permission)) {
            switch (requestCode) {
                //ACCESS_FINE_LOCATION
                case NearByConstants.REQUEST_ACCESS_FINE_LOCATION_PERMISSION_CODE:
                    Log.i(TAG, "ACCESS_FINE_LOCATION permission granted !");
                    //Request location updates...
                    break;
                default:
                    break;
            }

            //Permission granted
            mainView.showNearByFragment();
        } else {
            //Permission denied
            mainView.showNoPermissionMessage();
        }

    }
}
