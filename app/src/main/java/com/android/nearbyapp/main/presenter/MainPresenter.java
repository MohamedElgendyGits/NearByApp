package com.android.nearbyapp.main.presenter;

import android.app.Activity;

/**
 * Created by Mohamed Elgendy.
 */

public interface MainPresenter {

    void askForRuntimePermission(Activity activity, String permission, int requestCode);
    void validateRuntimePermissionResult (Activity activity, String permission, int requestCode);
}
