package com.carlos.capstone.utils;

import android.app.Activity;

import com.carlos.capstone.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Carlos on 11/04/2016.
 */
public class AdvertisementListener extends AdListener {
    private Activity context;
    private String action;

    public AdvertisementListener() {
        super();
    }
    public AdvertisementListener(Activity context,String action){
        super();
        this.context=context;
        this.action=action;
    }

    @Override
    public void onAdLoaded() {
        // Code to be executed when an ad finishes loading.
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        // Code to be executed when an ad request fails.
    }

    @Override
    public void onAdOpened() {
        Tracker tracker=((MyApplication)context.getApplication()).getTracker();
        //send to GA name of the index clicked
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(context.getString(R.string.ad_ga_category))
                .setAction(action)
                .build());
        // Code to be executed when an ad opens an overlay that
        // covers the screen.
    }

    @Override
    public void onAdLeftApplication() {
        // Code to be executed when the user has left the app.
    }

    @Override
    public void onAdClosed() {
        // Code to be executed when when the user is about to return
        // to the app after tapping on an ad.
    }
}
