package com.example.go4lunch.goforlunch.models.places.RestaurantsDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailsResponse implements Serializable {

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("result")
    @Expose
    private DetailsResult detailsResult;
    @SerializedName("status")
    @Expose
    private String status;

    public DetailsResponse() {
    }

    public DetailsResponse(List<Object> htmlAttributions, DetailsResult detailsResult, String status) {
        super();
        this.htmlAttributions = htmlAttributions;
        this.detailsResult = detailsResult;
        this.status = status;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public DetailsResponse withHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
        return this;
    }

    public DetailsResult getDetailsResult() {
        return detailsResult;
    }

    public void setDetailsResult(DetailsResult detailsResult) {
        this.detailsResult = detailsResult;
    }

    public DetailsResponse withDetailsResult(DetailsResult detailsResult) {
        this.detailsResult = detailsResult;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DetailsResponse withStatus(String status) {
        this.status = status;
        return this;
    }
}