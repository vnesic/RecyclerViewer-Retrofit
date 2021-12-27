package com.vnesic.htetest.network.retrofit.results;

import com.google.gson.annotations.SerializedName;

public class HtecUser {

    @SerializedName("name")
    private String mName;

    @SerializedName("username")
    private String mUsername;

    @SerializedName("email")
    private String mEmail;

    public HtecUser() {

    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    @Override
    public String toString() {
        return "HtecUser{" +
                "mName=" + mName +
                ", mUsername=" + mUsername +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
