package com.android.nearbyapp.application;

/**
 * Created by Mohamed Elgendy.
 */

public class NearByConstants {

    public static final int REQUEST_ACCESS_FINE_LOCATION_PERMISSION_CODE = 100;
    public static final String SAVED_LAYOUT_MANAGER = "layout_state";

    // four square api credentials
    public static final String CLIENT_ID = "AQHM4MUL4L40MYCVNR3VLNPABDSEDME3HUI2MJRLFHGDEIHV";
    public static final String CLIENT_SECRET = "XOILCMWBS2PQLX0QXXCAONY3BCW1EXMEAXUZ5LL3CCOUA4ND";
    public static final String API_CREATION_DATE = "20170917";
    public static final String API_RADIUS = "1000";
    public static final String IMAGE_SIZE = "original";


    // retrofit api end-points
    public static final String BASE_URL = "https://api.foursquare.com/v2/";
    public static final String VENUES_URL = BASE_URL+"venues/search?client_id="+CLIENT_ID
            +"&client_secret="+CLIENT_SECRET
            +"&v="+API_CREATION_DATE
            +"&radius="+API_RADIUS;


    // location request parameters
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000; //10 seconds
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public static final float SMALLEST_DISPLACEMENT = 500; //  500 meters
}
