package com.example.stas.homeproj.sync;

import android.accounts.Account;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.stas.homeproj.InvoicesActivity;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.sync.model.GoodsSync;
import com.example.stas.homeproj.sync.model.InvoiceSync;

import java.util.List;

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

        InvoiceSync.sync(getContext());

        List<InvoiceBuyApi> list = InvoiceSync.getInvoices(getContext());

        for (InvoiceBuyApi item : list) {
            GoodsSync.sync(getContext(), item);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(android.R.drawable.dialog_holo_dark_frame)
                .setContentTitle("HomeProj")
                .setContentText("Данные синхронизованы!");
        Intent resultIntent = new Intent(getContext(), InvoicesActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addParentStack(InvoicesActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = builder.build();
        notify.tickerText = "";
        mNotificationManager.notify(1, notify);
    }
}