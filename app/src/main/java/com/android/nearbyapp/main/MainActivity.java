package com.android.nearbyapp.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.nearbyapp.R;
import com.android.nearbyapp.application.NearByApplication;
import com.android.nearbyapp.main.presenter.MainPresenter;
import com.android.nearbyapp.main.presenter.MainPresenterImpl;
import com.android.nearbyapp.main.view.MainView;
import com.android.nearbyapp.nearby.NearByFragment;
import com.android.nearbyapp.nearby.model.local.SettingsSharedPref;
import com.android.nearbyapp.utils.ConnectionUtils;
import com.android.nearbyapp.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @BindView(R.id.location_error_container)
    RelativeLayout locationErrorContainer;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if(savedInstanceState == null){
            mainPresenter = new MainPresenterImpl(this,this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NearByApplication.activityResumed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NearByApplication.activityStopped();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mainPresenter.validateRuntimePermissionResult(this, permissions[0], requestCode);
    }

    @Override
    public void showNearByFragment() {
        FragmentUtils.replaceFragment(this, new NearByFragment(), R.id.fragment_container, false, "");
    }

    @Override
    public void showNoPermissionMessage() {
        fragmentContainer.setVisibility(View.GONE);
        locationErrorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isRealTime = SettingsSharedPref.isRealTimeMode(NearByApplication.getInstance());
        if (isRealTime) {
            menu.findItem(R.id.action_real_time).setChecked(true);
        }else{
            menu.findItem(R.id.action_single_update).setChecked(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
