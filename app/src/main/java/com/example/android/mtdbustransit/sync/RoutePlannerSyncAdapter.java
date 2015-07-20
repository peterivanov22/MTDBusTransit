package com.example.android.mtdbustransit.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by peter on 7/19/2015.
 */
public class RoutePlannerSyncAdapter extends AbstractThreadedSyncAdapter {

    public RoutePlannerSyncAdapter(Context context, boolean autoinitialize){
        super(context, autoinitialize);
    }

    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

    }
}
