package com.android.nearbyapp.nearby.model.rest.entities.venues;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

public class VenueLocation {

    @SerializedName("address")
    String address;

    @SerializedName("country")
    String country;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
