package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.carlos.capstone.sync.CapstoneSyncAdapter;
import com.carlos.capstone.utils.TimeMeasure;

/**
 * Created by Carlos on 04/05/2016.
 */
public class UpdateWidgetService extends IntentService {
    private static final String LOG_TAG=UpdateWidgetService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateWidgetService(String name) {
        super(name);
    }
    public UpdateWidgetService(){
        super(LOG_TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG,"onUpdateWidgetService");
        TimeMeasure tm=new TimeMeasure(LOG_TAG);
        CapstoneSyncAdapter.UpdateWidgetFavorites(getApplicationContext(),tm);

    }
}
