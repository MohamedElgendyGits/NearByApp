package com.android.nearbyapp.nearby.model.rest.entities.photos;

import com.android.nearbyapp.nearby.model.rest.entities.Meta;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

public class PhotosResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("response")
    private PhotoList photoListResponse;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public PhotoList getPhotoListResponse() {
        return photoListResponse;
    }

    public void setPhotoListResponse(PhotoList photoListResponse) {
        this.photoListResponse = photoListResponse;
    }
}
