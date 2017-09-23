package com.android.nearbyapp.nearby.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Elgendy.
 */

public class VenueViewModel implements Parcelable{

    private String name;
    private String address;
    private String placeImageUrl;

    public VenueViewModel(String name, String address, String placeImageUrl) {
        this.name = name;
        this.address = address;
        this.placeImageUrl = placeImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceImageUrl() {
        return placeImageUrl;
    }

    public void setPlaceImageUrl(String placeImageUrl) {
        this.placeImageUrl = placeImageUrl;
    }



    //parcelable creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public VenueViewModel createFromParcel(Parcel in) {
            return new VenueViewModel(in);
        }

        public VenueViewModel[] newArray(int size) {
            return new VenueViewModel[size];
        }
    };


    public VenueViewModel(Parcel in){
        this.name = in.readString();
        this.address = in.readString();
        this.placeImageUrl =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.placeImageUrl);
    }
}
