package com.example.android.mtdbustransit.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.android.mtdbustransit.sync.MTDBusTransitAuthenticator;

/**
 * The service which allows the sync adapter framework to access the authenticator.
 */
public class MTDBusTransitAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private MTDBusTransitAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new MTDBusTransitAuthenticator(this);
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