package com.android.nearbyapp.nearby.model.rest.entities.venues;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

public class Venue {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private VenueLocation venueLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VenueLocation getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(VenueLocation venueLocation) {
        this.venueLocation = venueLocation;
    }
}
