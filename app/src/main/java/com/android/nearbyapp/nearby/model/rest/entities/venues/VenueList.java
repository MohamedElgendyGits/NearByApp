package com.android.nearbyapp.nearby.model.rest.entities.venues;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy.
 */

public class VenueList {

    @SerializedName("venues")
    private ArrayList<Venue> venues;

    @SerializedName("confident")
    private String confident;


    public ArrayList<Venue> getVenues() {
        return venues;
    }

    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    public String getConfident() {
        return confident;
    }

    public void setConfident(String confident) {
        this.confident = confident;
    }
}
