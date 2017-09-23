package com.android.nearbyapp.nearby.model.rest.entities.photos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

public class PhotoList {

    @SerializedName("photos")
    private Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
