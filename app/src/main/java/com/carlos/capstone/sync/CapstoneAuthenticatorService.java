package com.carlos.capstone.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Carlos on 25/01/2016.
 */
public class CapstoneAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private CapstoneAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new CapstoneAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
