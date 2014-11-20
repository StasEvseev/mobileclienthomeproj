package com.example.stas.homeproj.sync.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoicesBuyApi;
import com.example.stas.homeproj.provider.InvoiceContentProvider;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;
import com.example.stas.homeproj.sync.BaseSync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 20.11.14.
 */
public class InvoiceSync extends BaseSync {

//    private Context context;

//    public InvoiceSync(Context context) {
//        context = context;
//    }

    public final static String TAG = InvoiceSync.class.getName();

    public static boolean sync(final Context context) {

//        Cursor curInv = context.getContentResolver().query(InvoiceContentProvider.CONTENT_URI, new String[] {"MAX(id) AS id"}, null, null, null);

//        curInv.moveToNext();

        int max_id = getLastIdInvoice(context);

        Log.d(TAG, String.valueOf(max_id));
        final InvoiceBuyApiHolder holder = new InvoiceBuyApiHolder();

        Map<String, String> params = new HashMap<String, String>();
        params.put("from", String.valueOf(max_id));
        IInvoiceRestAPI rest = RestApiHelper.createResource(IInvoiceRestAPI.class, context);

        InvoicesBuyApi invoicesBuyApi = rest.invoices(params);

        for(InvoiceBuyApi item : invoicesBuyApi.items) {
            context.getContentResolver().insert(InvoiceContentProvider.CONTENT_URI, holder.toCursor(item));
        }

//        rest.invoices(params, new Callback<InvoicesBuyApi>() {
//            @Override
//            public void success(InvoicesBuyApi invoicesBuyApi, Response response) {
//                for(InvoiceBuyApi item : invoicesBuyApi.items) {
//                    context.getContentResolver().insert(InvoiceContentProvider.CONTENT_URI, holder.toCursor(item));
//                }
//            }

//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//
//
//
//        if(curInv!= null) {
//            while(curInv.moveToNext()) {
//                invoice = holder.fromCursor(curInv);
//            }
//        }

        return true;
    }

    public static List<InvoiceBuyApi> getInvoices(Context context) {
        final InvoiceBuyApiHolder holder = new InvoiceBuyApiHolder();
        List<InvoiceBuyApi> list = new ArrayList<InvoiceBuyApi>();
        Cursor curinvs = context.getContentResolver().query(InvoiceContentProvider.CONTENT_URI, null, null, null, null);

        if(curinvs!=null) {
            while(curinvs.moveToNext()) {
                list.add(holder.fromCursor(curinvs));
            }
        }

        return list;
    }

}
