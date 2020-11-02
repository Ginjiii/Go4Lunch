package com.example.go4lunch.goforlunch.models.PlaceDetail;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Period implements Serializable {

    @SerializedName("close")
    private Closed mClosed;
    @SerializedName("open")
    private Open mOpen;

    public Closed getClosed() {
        return mClosed;
    }

    public void setClose(Closed close) {
        mClosed = close;
    }

    public Open getOpen() {
        return mOpen;
    }

    public void setOpen(Open open) {
        mOpen = open;
    }
    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", mOpen, mClosed);
    }
}