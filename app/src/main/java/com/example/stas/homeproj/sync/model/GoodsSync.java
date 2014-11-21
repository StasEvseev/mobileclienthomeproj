package com.example.stas.homeproj.sync.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.db.dao.GoodBuyApiHolder;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodsBuyApi;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoiceCountResult;
import com.example.stas.homeproj.provider.GoodContentProvider;
import com.example.stas.homeproj.resources.IInvoiceItemCountRestAPI;
import com.example.stas.homeproj.resources.IInvoiceItemRestAPI;


/**
 * @author StasEvseev
 * Синхронизатор
 */

public class GoodsSync extends BaseSync {
    private static String TAG = GoodsSync.class.getName();
    public static synchronized boolean sync(final Context context, final InvoiceBuyApi inv) {

        Log.d(TAG, "start");

//        final GoodBuyApiHolder holder = new GoodBuyApiHolder();

        Cursor curGood = context.getContentResolver().query(GoodContentProvider.CONTENT_URI, null,
                GoodBuyApiHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)}, null);

        curGood.moveToNext();
        int countLocal = curGood.getCount();

        curGood.close();

        IInvoiceItemCountRestAPI restCount = RestApiHelper.createResource(IInvoiceItemCountRestAPI.class, context);

        InvoiceCountResult result = restCount.count(inv.id);
        int countRemote = result.result.count;

        Log.d(TAG, "REMOTE - " + countRemote + " LOCAL - " + countLocal);

        if (countRemote != countLocal) {
            context.getContentResolver().delete(GoodContentProvider.CONTENT_URI,
                    GoodBuyApiHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)});

            IInvoiceItemRestAPI restItems = RestApiHelper.createResource(IInvoiceItemRestAPI.class, context);

            GoodsBuyApi goods = restItems.goodsSync(inv.id);

            for(GoodBuyApi good: goods.items) {
                ContentValues cv = GoodBuyApiHolder.toCursor(good);
                cv.put(GoodBuyApiHolder.COL_INVOICE_ID, inv.id);
                context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, cv);
            }
        }

        Log.d(TAG, "end");

        return true;
    }
}