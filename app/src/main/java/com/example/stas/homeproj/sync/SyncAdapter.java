package com.example.stas.homeproj.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.example.stas.homeproj.db.dao.GoodBuyApiHolder;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.provider.GoodContentProvider;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        Log.d("SyncAdapter", "2 argument");
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        Log.d("SyncAdapter", "3 argument");
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        Log.d("onPerformSync", "SYNC! SYNC! SYNC!");
//        GoodsSync.sync(getContext());

//        try {
//
////            ContentValues cv = new ContentValues();
////            cv.put(GoodBuyApiDBHelper.COL_ID, 1);
////            cv.put(GoodBuyApiDBHelper.COL_NAME, "Stas");
////            cv.put(GoodBuyApiDBHelper.COL_YEAR, 2014);
////            provider.insert(GoodContentProvider.CONTENT_URI, cv);
//
//
//
////            GoodBuyApi good = new GoodBuyApi();
////            good.id = 1;
////            good.name = "Test";
////
////            provider.insert(GoodContentProvider.CONTENT_URI, new GoodBuyApiHolder().toCursor(good));
////
////            good.id = 2;
////            good.name = "Test 2";
////
////            provider.insert(GoodContentProvider.CONTENT_URI, new GoodBuyApiHolder().toCursor(good));
////
////            Cursor curGood = provider.query(GoodContentProvider.CONTENT_URI, null, null, null, null);
////
////            if(curGood!= null) {
////                while(curGood.moveToNext()) {
////                    Log.d(SyncAdapter.class.getName(),
////                    "ID" + curGood.getInt(curGood.getColumnIndex(GoodBuyApiHolder.COL_ID)) +
////                    "NAME" + curGood.getString(curGood.getColumnIndex(GoodBuyApiHolder.COL_NAME)));
////                }
////            }
//
//        } catch (RemoteException e) {
//            Log.d(this.getClass().getName(), "onPerformSync EXCEPTION");
//            e.printStackTrace();
//        }
    }
}