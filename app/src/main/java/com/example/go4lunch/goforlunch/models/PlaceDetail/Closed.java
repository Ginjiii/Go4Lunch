package com.example.go4lunch.goforlunch.models.PlaceDetail;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Closed {

    @SerializedName("day")
    private Long mDay;
    @SerializedName("time")
    private String mTime;

    public Long getDay() {
        return mDay;
    }

    public void setDay(Long day) {
        mDay = day;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", mDay, mTime);
    }
}
