package com.carlos.capstone.adapters;

/**
 * Created by Carlos on 28/12/2015.
 */
public class StatsChild {
    private String mMeasure;
    private String mValue;

    public StatsChild(String mMeasure, String mValue) {
        this.mMeasure = mMeasure;
        this.mValue = mValue;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }
}
