package com.android.nearbyapp.nearby.model;

import com.android.nearbyapp.nearby.adapter.VenueViewModel;
import com.android.nearbyapp.nearby.model.rest.entities.photos.PhotoItem;
import com.android.nearbyapp.nearby.model.rest.entities.photos.PhotosResponse;
import com.android.nearbyapp.nearby.model.rest.entities.venues.Venue;
import com.android.nearbyapp.nearby.model.rest.entities.venues.VenuesResponse;
import com.android.nearbyapp.nearby.model.rest.retrofit.ApiClient;
import com.android.nearbyapp.nearby.model.rest.retrofit.ApiInterface;
import com.android.nearbyapp.nearby.presenter.NearByPresenter;

import java.util.ArrayList;

import io.reactivex.Observable;

import static com.android.nearbyapp.application.NearByConstants.IMAGE_SIZE;
import static com.android.nearbyapp.application.NearByConstants.VENUES_URL;
import static com.android.nearbyapp.application.NearByConstants.CLIENT_ID;
import static com.android.nearbyapp.application.NearByConstants.CLIENT_SECRET;
import static com.android.nearbyapp.application.NearByConstants.API_CREATION_DATE;


/**
 * Created by Mohamed Elgendy.
 */

public class NearByModelImpl implements NearByModel {

    private NearByPresenter nearByPresenter;
    ApiInterface apiService;

    public NearByModelImpl(NearByPresenter nearByPresenter) {
        this.nearByPresenter = nearByPresenter;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public Observable<VenuesResponse> getVenues(String locationLatLong) {
        return apiService.getNearByVenues(VENUES_URL, locationLatLong);
    }

    @Override
    public Observable<PhotosResponse> getPhotos(String venueId) {
        return apiService.getPhotos(venueId, CLIENT_ID, CLIENT_SECRET, API_CREATION_DATE);
    }

    @Override
    public VenueViewModel createVenueViewModel(Venue venue, PhotosResponse photosResponse) {
        ArrayList<PhotoItem> photoItems = photosResponse.getPhotoListResponse().getPhotos().getPhotoItems();

        String VenuePhotoUrl = null;
        if (!photoItems.isEmpty()) {
            PhotoItem photoItem = photoItems.get(0);
            VenuePhotoUrl = photoItem.getPrefix() + IMAGE_SIZE + photoItem.getSuffix();
        }

        return new VenueViewModel(venue.getName(),venue.getVenueLocation().getAddress(),VenuePhotoUrl);
    }
}
