package com.example.stas.homeproj.sync.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.Session;
import com.example.stas.homeproj.db.dao.GoodHolder;
import com.example.stas.homeproj.db.dao.GoodLocalHolder;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.models.GoodsBuyApi;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoiceCountResult;
import com.example.stas.homeproj.provider.GoodContentProvider;
import com.example.stas.homeproj.provider.GoodLocalContentProvider;
import com.example.stas.homeproj.resources.IInvoiceItemCountRestAPI;
import com.example.stas.homeproj.resources.IInvoiceItemRestAPI;

import java.io.IOException;


/**
 * @author StasEvseev
 * Синхронизатор
 */

public class GoodsSync extends BaseSync {
    private static String TAG = GoodsSync.class.getName();
    public static synchronized boolean sync(final Context context, final InvoiceBuyApi inv, String authToken) {

        Log.d(TAG, "start");

        Cursor curGood = context.getContentResolver().query(GoodContentProvider.CONTENT_URI, null,
                GoodHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)}, null);

        curGood.moveToNext();
        int countLocal = curGood.getCount();

        curGood.close();

        IInvoiceItemCountRestAPI restCount = RestApiHelper.createResource(IInvoiceItemCountRestAPI.class, context, authToken);

        InvoiceCountResult result = restCount.count(inv.id);
        int countRemote = result.result.count;

        Log.d(TAG, "REMOTE - " + countRemote + " LOCAL - " + countLocal);

        if (countRemote != countLocal) {
            context.getContentResolver().delete(GoodContentProvider.CONTENT_URI,
                    GoodHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)});

            IInvoiceItemRestAPI restItems = RestApiHelper.createResource(IInvoiceItemRestAPI.class, context, authToken);

            GoodsBuyApi goods = restItems.goodsSync(inv.id);

            for(GoodBuyApi good: goods.items) {
                ContentValues cv = GoodHolder.toCursor(good);
                cv.put(GoodHolder.COL_INVOICE_ID, inv.id);

                GoodLocal goodLocal = new GoodLocal(good);

                ContentValues cvlocal = GoodLocalHolder.toCursor(goodLocal);

                context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, cv);
                context.getContentResolver().insert(GoodLocalContentProvider.CONTENT_URI, cvlocal);
            }
        }

        Log.d(TAG, "end");

        return true;
    }
}
