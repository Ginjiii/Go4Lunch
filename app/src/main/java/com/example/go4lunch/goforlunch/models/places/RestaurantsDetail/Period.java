package com.example.go4lunch.goforlunch.models.places.RestaurantsDetail;

import com.example.go4lunch.goforlunch.models.places.RestaurantsDetail.Close;
import com.example.go4lunch.goforlunch.models.places.RestaurantsDetail.Open;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Period {

    @SerializedName("close")
    @Expose
    private Close close;
    @SerializedName("open")
    @Expose
    private Open open;

    public Period() {}

    public Period(Close close, Open open) {}

    public Close getClose() {
        return close;
    }

    public void setClose(Close close) {
        this.close = close;
    }

    public Open getOpen() {
        return open;
    }

    public void setOpen(Open open) {
        this.open = open;
    }
}
