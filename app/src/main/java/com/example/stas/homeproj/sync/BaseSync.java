package com.example.stas.homeproj.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by user on 21.11.14.
 */
public abstract class BaseSync extends AbstractThreadedSyncAdapter {

    public static final String LOG = BaseSync.class.getName();

    public BaseSync(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public void prePerformSync() {

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG, "onPerformSync");
//        super.onPerformSync(account, extras, authority, provider, syncResult);
    }
}
