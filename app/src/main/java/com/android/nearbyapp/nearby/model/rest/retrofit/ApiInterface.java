package com.android.nearbyapp.nearby.model.rest.retrofit;

import com.android.nearbyapp.nearby.model.rest.entities.photos.PhotosResponse;
import com.android.nearbyapp.nearby.model.rest.entities.venues.VenuesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Mohamed Elgendy.
 */

public interface ApiInterface {

    @GET
    Observable<VenuesResponse> getNearByVenues (@Url String url, @Query("ll") String location);

    @GET("venues/{venueId}/photos")
    Observable<PhotosResponse> getPhotos (@Path("venueId") String venueId,
                                          @Query("client_id") String clientId,
                                          @Query("client_secret") String clientSecret,
                                          @Query("v") String version);
}
