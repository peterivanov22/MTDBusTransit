package com.example.android.mtdbustransit.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MTDBusTransitSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MTDBusTransitSyncAdapter sMTDSyncAdapter = null;
    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sMTDSyncAdapter == null) {
                sMTDSyncAdapter = new MTDBusTransitSyncAdapter (getApplicationContext(), true);
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return sMTDSyncAdapter.getSyncAdapterBinder();
    }
}