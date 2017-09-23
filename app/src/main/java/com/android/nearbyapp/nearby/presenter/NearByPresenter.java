package com.android.nearbyapp.nearby.presenter;

/**
 * Created by Mohamed Elgendy.
 */

public interface NearByPresenter {
    void startLocationRefresh();
    void updateApplicationSettings(boolean isRealTime);
    void clearRxDisposals();
}
