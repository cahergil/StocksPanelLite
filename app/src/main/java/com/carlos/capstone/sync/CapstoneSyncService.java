package com.carlos.capstone.sync;

/**
 * Created by Carlos on 25/01/2016.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;



public class CapstoneSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static CapstoneSyncAdapter sCapstoneSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("CapstoneSyncService", "onCreate - CapstoneSyncService");
        synchronized (sSyncAdapterLock) {
            if (sCapstoneSyncAdapter == null) {
                sCapstoneSyncAdapter = new CapstoneSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sCapstoneSyncAdapter.getSyncAdapterBinder();
    }
}