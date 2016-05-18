package com.carlos.capstone.utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Carlos on 26/01/2016.
 */
public class TimeMeasure {
    private final DecimalFormat format;
    private final double start;
    private final String tag;

    public TimeMeasure(String tag) {
        this.format = new DecimalFormat("0.0");
        this.start = System.currentTimeMillis();
        this.tag = tag;
        log("start");
    }

    public String log(String message) {
        double elapsed = ((double) (System.currentTimeMillis() - start)) / 1000.0;
        Log.d(tag, format.format(elapsed) + ": " + message);
        return format.format(elapsed);
    }
}