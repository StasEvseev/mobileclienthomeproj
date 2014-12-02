package com.example.stas.homeproj.sync.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.db.dao.InvoiceHolder;
import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoicesBuyApi;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author StasEvseev
 * Синхронизатор накладных
 */
public class InvoiceSync extends BaseSync {

    public final static String TAG = InvoiceSync.class.getName();

    public static boolean sync(final Context context, String authToken) {

        int max_id = getLastIdInvoice(context);

        Log.d(TAG, String.valueOf(max_id));

        Map<String, String> params = new HashMap<String, String>();
        params.put("from", String.valueOf(max_id));
        IInvoiceRestAPI rest = RestApiHelper.createResource(IInvoiceRestAPI.class, context, authToken, null);

        InvoicesBuyApi invoicesBuyApi = rest.invoices(params);

        for(InvoiceBuyApi item : invoicesBuyApi.items) {

            Invoice invoice = new Invoice();
            invoice.id_buy_api = item.id;
            invoice.number = item.number;
            invoice.date = item.date;

            context.getContentResolver().insert(MainContentProvider.CONTENT_URI_INVOICE, InvoiceHolder.toCursor(invoice));
        }

        return true;
    }

    public static List<Invoice> getInvoices(Context context) {
        List<Invoice> list = new ArrayList<Invoice>();
        Cursor curinvs = context.getContentResolver().query(MainContentProvider.CONTENT_URI_INVOICE, null, null, null, null);

        if(curinvs!=null) {
            while(curinvs.moveToNext()) {
                Invoice inv = new Invoice();
                InvoiceHolder.fromCursor(curinvs, inv);
                list.add(inv);
            }
            curinvs.close();
        }

        return list;
    }

}
