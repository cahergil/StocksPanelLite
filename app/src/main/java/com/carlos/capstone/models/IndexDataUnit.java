package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 23/01/2016.
 */
public class IndexDataUnit implements Parcelable {
    public String market;
    public String name;
    public String ticker;
    private List<Double> yValues =new ArrayList<Double>();
    private List<String> xLabels=new ArrayList<String>();
    private List<Double> yValuesMarkerView=new ArrayList<Double>();



    public IndexDataUnit(){

    }
    public IndexDataUnit(String market, String name, String ticker, List<Double> yValues, List<String> xLabels, List<Double> yValuesMarkerView) {
        this.market = market;
        this.name = name;
        this.ticker = ticker;
        this.yValues = yValues;
        this.xLabels = xLabels;
        this.yValuesMarkerView = yValuesMarkerView;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<Double> getyValuesMarkerView() {
        return yValuesMarkerView;
    }

    public void setyValuesMarkerView(List<Double> yValuesMarkerView) {
        this.yValuesMarkerView = yValuesMarkerView;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getyValues() {
        return yValues;
    }

    public void setyValues(List<Double> yValues) {
        this.yValues = yValues;
    }

    public List<String> getxLabels() {
        return xLabels;
    }

    public void setxLabels(List<String> xLabels) {
        this.xLabels = xLabels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.market);
        dest.writeString(this.name);
        dest.writeString(this.ticker);
        dest.writeList(this.yValues);
        dest.writeStringList(this.xLabels);
        dest.writeList(this.yValuesMarkerView);
    }

    protected IndexDataUnit(Parcel in) {
        this.market = in.readString();
        this.name = in.readString();
        this.ticker = in.readString();
        this.yValues = new ArrayList<Double>();
        in.readList(this.yValues, List.class.getClassLoader());
        this.xLabels = in.createStringArrayList();
        this.yValuesMarkerView = new ArrayList<Double>();
        in.readList(this.yValuesMarkerView, List.class.getClassLoader());
    }

    public static final Creator<IndexDataUnit> CREATOR = new Creator<IndexDataUnit>() {
        public IndexDataUnit createFromParcel(Parcel source) {
            return new IndexDataUnit(source);
        }

        public IndexDataUnit[] newArray(int size) {
            return new IndexDataUnit[size];
        }
    };
}
