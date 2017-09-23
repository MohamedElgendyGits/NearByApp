package com.android.nearbyapp.nearby.model;

import com.android.nearbyapp.nearby.adapter.VenueViewModel;
import com.android.nearbyapp.nearby.model.rest.entities.photos.PhotosResponse;
import com.android.nearbyapp.nearby.model.rest.entities.venues.Venue;
import com.android.nearbyapp.nearby.model.rest.entities.venues.VenuesResponse;

import io.reactivex.Observable;

/**
 * Created by Mohamed Elgendy.
 */

public interface NearByModel {

    Observable<VenuesResponse> getVenues(String locationLatLong);
    Observable<PhotosResponse> getPhotos(String venueId);
    VenueViewModel createVenueViewModel(Venue venue, PhotosResponse photosResponse);
}
