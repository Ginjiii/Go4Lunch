package models.AutoComplete;

import android.gesture.Prediction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutoCompleteResult {

    @SerializedName("predictions")
    private List<Prediction> mPredictions;
    @SerializedName("status")
    private String mStatus;

    public List<Prediction> getPredictions() {
        return mPredictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        mPredictions = predictions;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}