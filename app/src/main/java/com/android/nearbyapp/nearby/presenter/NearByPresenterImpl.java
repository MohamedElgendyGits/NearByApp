package com.android.nearbyapp.nearby.presenter;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.android.nearbyapp.R;
import com.android.nearbyapp.application.NearByApplication;
import com.android.nearbyapp.nearby.adapter.VenueViewModel;
import com.android.nearbyapp.nearby.model.NearByModel;
import com.android.nearbyapp.nearby.model.NearByModelImpl;
import com.android.nearbyapp.nearby.model.local.SettingsSharedPref;
import com.android.nearbyapp.nearby.model.rest.entities.photos.PhotosResponse;
import com.android.nearbyapp.nearby.model.rest.entities.venues.Venue;
import com.android.nearbyapp.nearby.model.rest.entities.venues.VenuesResponse;
import com.android.nearbyapp.nearby.view.NearByView;
import com.android.nearbyapp.utils.ConnectionUtils;
import com.android.nearbyapp.utils.TextUtils;
import com.google.android.gms.location.LocationRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.nearbyapp.application.NearByConstants.UPDATE_INTERVAL_IN_MILLISECONDS;
import static com.android.nearbyapp.application.NearByConstants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
import static com.android.nearbyapp.application.NearByConstants.SMALLEST_DISPLACEMENT;

/**
 * Created by Mohamed Elgendy.
 */

public class NearByPresenterImpl implements NearByPresenter {

    private NearByView nearByView;
    private NearByModel nearByModel;
    private CompositeDisposable compositeDisposable;
    private LocationRequest locationRequest;


    public NearByPresenterImpl(NearByView nearByView) {
        this.nearByView = nearByView;
        nearByModel = new NearByModelImpl(this);
        compositeDisposable = new CompositeDisposable();

        startLocationRefresh();
    }

    @Override
    public void startLocationRefresh() {

        if (ConnectionUtils.isConnected(NearByApplication.getInstance())) {
            buildLocationRequest(SettingsSharedPref.isRealTimeMode(NearByApplication.getInstance()));
            startListening();
        } else {
            nearByView.showInlineConnectionError(TextUtils.getString(R.string.connection_failed));
        }

    }

    private void buildLocationRequest(boolean realTimeMode) {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        if (realTimeMode) {
            locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        }
    }

    @Override
    public void updateApplicationSettings(boolean isRealTime) {

        if (isRealTime) {
            locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        } else {
            locationRequest.setSmallestDisplacement(0);
        }

        clearRxDisposals();
        startListening();
    }

    private void startListening() {

        Single<Boolean> settingsObservable = NearByApplication.getRxLocationInstance().settings().
                checkAndHandleResolution(locationRequest)
                .observeOn(AndroidSchedulers.mainThread());

        Disposable settingsDisposable = settingsObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    prepareLocationLatLong();
                } else {
                    nearByView.onLocationSettingsUnsuccessful();
                }
            }
        });
        compositeDisposable.add(settingsDisposable);
    }

    private void prepareLocationLatLong() {

        nearByView.showProgressLoading();

        //noinspection MissingPermission
        Observable<Location> locationObservable = NearByApplication.getRxLocationInstance()
                .location().updates(locationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable locationDisposable = locationObservable.subscribe(new Consumer<Location>() {
            @Override
            public void accept(@NonNull Location location) throws Exception {
                retrieveNearByPlaces(location);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                nearByView.hideProgressLoading();
                nearByView.showInlineError(TextUtils.getString(R.string.unknown_error));
            }
        });

        compositeDisposable.add(locationDisposable);
    }

    public void retrieveNearByPlaces(Location location) {

        String locationLatLong = location.getLatitude() + "," + location.getLatitude();
        Single<List<VenueViewModel>> venueViewModelObservable =
                nearByModel.getVenues(locationLatLong)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Function<VenuesResponse, Observable<Venue>>() {
                            @Override
                            public Observable<Venue> apply(@NonNull VenuesResponse venuesResponse) throws Exception {
                                return Observable.fromIterable(venuesResponse.getVenueListResponse().getVenues());
                            }
                        })
                        .observeOn(Schedulers.io())
                        .flatMap(new Function<Venue, Observable<VenueViewModel>>() {
                            @Override
                            public Observable<VenueViewModel> apply(@NonNull final Venue venue) throws Exception {
                                return nearByModel.getPhotos(venue.getId()).flatMap(new Function<PhotosResponse, Observable<VenueViewModel>>() {
                                    @Override
                                    public Observable<VenueViewModel> apply(@NonNull PhotosResponse photosResponse) throws Exception {
                                        return Observable.just(nearByModel.createVenueViewModel(venue, photosResponse));
                                    }
                                });
                            }
                        }).toList()
                        .observeOn(AndroidSchedulers.mainThread());


        Disposable placesDisposable = venueViewModelObservable.subscribeWith(new DisposableSingleObserver<List<VenueViewModel>>() {
            @Override
            public void onSuccess(@NonNull List<VenueViewModel> venueViewModels) {
                nearByView.hideProgressLoading();
                nearByView.updateVenuesList(venueViewModels);
                checkMode();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                nearByView.hideProgressLoading();
                nearByView.showInlineError(TextUtils.getString(R.string.unknown_error));
                checkMode();
            }
        });

        compositeDisposable.add(placesDisposable);
    }

    private void checkMode() {
        if(!SettingsSharedPref.isRealTimeMode(NearByApplication.getInstance())){
            clearRxDisposals();
        }
    }

    @Override
    public void clearRxDisposals() {
        compositeDisposable.clear();
    }
}
