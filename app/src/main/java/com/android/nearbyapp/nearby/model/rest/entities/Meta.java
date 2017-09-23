package com.android.nearbyapp.nearby.model.rest.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy.
 */

public class Meta {

    @SerializedName("code")
    private String code;

    @SerializedName("requestId")
    private String requestId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
