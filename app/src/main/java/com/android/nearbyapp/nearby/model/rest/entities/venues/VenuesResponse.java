package com.android.nearbyapp.nearby.model.rest.entities.venues;

import com.android.nearbyapp.nearby.model.rest.entities.Meta;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

public class VenuesResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("response")
    private VenueList venueListResponse;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public VenueList getVenueListResponse() {
        return venueListResponse;
    }

    public void setVenueListResponse(VenueList venueListResponse) {
        this.venueListResponse = venueListResponse;
    }
}
