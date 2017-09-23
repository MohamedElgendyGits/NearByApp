package com.android.nearbyapp.nearby.model.rest.entities.photos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy.
 */

public class Photos {

    @SerializedName("count")
    private int count;

    @SerializedName("items")
    private ArrayList<PhotoItem> photoItems;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<PhotoItem> getPhotoItems() {
        return photoItems;
    }

    public void setPhotoItems(ArrayList<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }
}
