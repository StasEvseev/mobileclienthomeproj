package com.example.stas.homeproj.sync.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.db.dao.InvoiceItemHolder;
import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.db.dao.model.InvoiceItem;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodsBuyApi;
import com.example.stas.homeproj.models.InvoiceCountResult;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.resources.IInvoiceItemCountRestAPI;
import com.example.stas.homeproj.resources.IInvoiceItemRestAPI;


/**
 * @author StasEvseev
 * Синхронизатор
 */

public class InvoiceItemSync extends BaseSync {
    private static String TAG = InvoiceItemSync.class.getName();
    public static synchronized boolean sync(final Context context, final Invoice inv, String authToken) {

        Log.d(TAG, "start");

        Cursor curItems = context.getContentResolver().query(MainContentProvider.CONTENT_URI_INVOICEITEM, null,
                InvoiceItemHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)}, null);

        curItems.moveToNext();
        int countLocal = curItems.getCount();

        curItems.close();

        IInvoiceItemCountRestAPI restCount = RestApiHelper.createResource(IInvoiceItemCountRestAPI.class, context, authToken, null);

        InvoiceCountResult result = restCount.count(inv.id);
        int countRemote = result.result.count;

        Log.d(TAG, "REMOTE - " + countRemote + " LOCAL - " + countLocal);
        IInvoiceItemRestAPI restItems = RestApiHelper.createResource(IInvoiceItemRestAPI.class, context, authToken, null);

        if (countRemote != countLocal) {
            context.getContentResolver().delete(MainContentProvider.CONTENT_URI_INVOICEITEM,
                    InvoiceItemHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)});

            GoodsBuyApi goods = restItems.goodsSync(inv.id);

            for(GoodBuyApi good: goods.items) {

                InvoiceItem invoiceItem = new InvoiceItem();

                invoiceItem.id_buy_api = good.id;
                invoiceItem.count = good.count;
                invoiceItem.placer = good.placer;
                invoiceItem.count_whole_pack = good.count_whole_pack;

                invoiceItem.invoice_id = inv.id;
                invoiceItem.fullname = good.full_name;
                invoiceItem.number_local = good.number_local;
                invoiceItem.number_global = good.number_global;

                ContentValues cv = InvoiceItemHolder.toCursor(invoiceItem);
                context.getContentResolver().insert(MainContentProvider.CONTENT_URI_INVOICEITEM, cv);
            }
        }

        Log.d(TAG, "end");

        return true;
    }
}
