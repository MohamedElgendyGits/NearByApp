package com.android.nearbyapp.nearby;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.nearbyapp.R;
import com.android.nearbyapp.application.NearByApplication;
import com.android.nearbyapp.main.MainActivity;
import com.android.nearbyapp.nearby.adapter.VenueViewModel;
import com.android.nearbyapp.nearby.adapter.VenuesListAdapter;
import com.android.nearbyapp.nearby.model.local.SettingsSharedPref;
import com.android.nearbyapp.nearby.presenter.NearByPresenter;
import com.android.nearbyapp.nearby.presenter.NearByPresenterImpl;
import com.android.nearbyapp.nearby.view.NearByView;
import com.android.nearbyapp.utils.DialogUtils;
import com.android.nearbyapp.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.nearbyapp.application.NearByConstants.SAVED_LAYOUT_MANAGER;

/**
 * Created by Mohamed Elgendy.
 */

public class NearByFragment extends Fragment implements NearByView {

    @BindView(R.id.layout_places_list)
    FrameLayout listLayout;

    @BindView(R.id.layout_error)
    RelativeLayout noListLayout;

    @BindView(R.id.places_recycler_view)
    RecyclerView placesRecyclerView;

    // declare venues list components
    private RecyclerView.Adapter venuesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<VenueViewModel> venueViewModelArrayList;
    private Parcelable mListState;

    // declare progress dialog
    private ProgressDialog progressDialog;

    // declare history presenter
    private NearByPresenter nearByPresenter;


    public NearByFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_by, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        NearByApplication.activityResumed();

        // initialize views
        placesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        placesRecyclerView.setLayoutManager(mLayoutManager);
        venueViewModelArrayList = new ArrayList<>();
        venuesAdapter = new VenuesListAdapter(venueViewModelArrayList);
        placesRecyclerView.setAdapter(venuesAdapter);


        if (savedInstanceState == null) {
            nearByPresenter = new NearByPresenterImpl(this);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_LAYOUT_MANAGER, venueViewModelArrayList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // Retrieve list state
        if(savedInstanceState != null) {
            venueViewModelArrayList = savedInstanceState.getParcelableArrayList(SAVED_LAYOUT_MANAGER);
            venuesAdapter = new VenuesListAdapter(venueViewModelArrayList);
            placesRecyclerView.setAdapter(venuesAdapter);
        }
    }

    @Override
    public void showProgressLoading() {

        if (NearByApplication.isActivityVisible()) {
            if (progressDialog == null)
                progressDialog = DialogUtils.getProgressDialog(getActivity(),
                        TextUtils.getString(R.string.loading_message), false,
                        false);

            progressDialog.show();
        }
    }

    @Override
    public void hideProgressLoading() {
        if (NearByApplication.isActivityVisible()) {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    @Override
    public void showInlineError(String error) {
        if (NearByApplication.isActivityVisible()) {
            DialogUtils.getSnackBar(getView(), error, null, null).show();
        }
    }

    @Override
    public void showInlineConnectionError(String error) {

        if (NearByApplication.isActivityVisible()) {
            Snackbar.make(getView(), error
                    , Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nearByPresenter.startLocationRefresh();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void updateVenuesList(List<VenueViewModel> venueViewModels) {

        if(NearByApplication.isActivityVisible()) {
            venueViewModelArrayList.clear();
            venuesAdapter.notifyDataSetChanged();

            venueViewModelArrayList.addAll(venueViewModels);
            venuesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLocationSettingsUnsuccessful() {

        if (NearByApplication.isActivityVisible()) {
            Snackbar.make(getView(), R.string.location_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nearByPresenter.startLocationRefresh();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_single_update:
                switchSelection(item);
                SettingsSharedPref.setRealTimeMode(NearByApplication.getInstance(), false);
                nearByPresenter.updateApplicationSettings(false);
                return true;
            case R.id.action_real_time:
                switchSelection(item);
                SettingsSharedPref.setRealTimeMode(NearByApplication.getInstance(), true);
                nearByPresenter.updateApplicationSettings(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchSelection(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (!getActivity().isChangingConfigurations()) {
            nearByPresenter.clearRxDisposals();
        }
    }
}

