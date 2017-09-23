package com.android.nearbyapp.nearby.view;

import android.location.Location;

import com.android.nearbyapp.nearby.adapter.VenueViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Elgendy.
 */

public interface NearByView {

    void showProgressLoading();
    void hideProgressLoading();
    void showInlineError(String error);
    void showInlineConnectionError(String error);
    void updateVenuesList(List<VenueViewModel> venueViewModel);
    void onLocationSettingsUnsuccessful();
}
