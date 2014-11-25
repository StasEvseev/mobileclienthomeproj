package com.example.stas.homeproj.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
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
import com.example.stas.homeproj.Session;
import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.sync.model.GoodLocalSync;
import com.example.stas.homeproj.sync.model.GoodsSync;
import com.example.stas.homeproj.sync.model.InvoiceSync;

import java.io.IOException;
import java.util.List;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    AccountManager mAccountManager;

    public final static String LOG = SyncAdapter.class.getName();

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        Log.d(LOG, "2 argument");
        mAccountManager = AccountManager.get(context);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        try {
            String authToken = AccountManager.get(getContext()).blockingGetAuthToken(account,
                    Session.TOKEN_TYPE, true);

            if (authToken == null || "".equals(authToken)) {
                return;
            }

            Log.d(LOG, "SYNC! SYNC! SYNC! = " + authToken);

            Context context = getContext();

            InvoiceSync.sync(context, authToken);

            List<InvoiceBuyApi> list = InvoiceSync.getInvoices(context);

            for (InvoiceBuyApi item : list) {
                GoodsSync.sync(context, item, authToken);
            }

            GoodLocalSync.sync(context, authToken);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.btn_star_big_off)
                    .setContentTitle("HomeProj")
                    .setContentText("Данные синхронизованы!");
            Intent resultIntent = new Intent(context, InvoicesActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(InvoicesActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = builder.build();
            notify.tickerText = "";
            mNotificationManager.notify(1, notify);

        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        }


    }
}