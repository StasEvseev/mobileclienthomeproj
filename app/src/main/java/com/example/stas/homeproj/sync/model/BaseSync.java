package com.example.stas.homeproj.sync.model;

import android.content.Context;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;
import com.example.stas.homeproj.provider.InvoiceContentProvider;

/**
 * Created by user on 20.11.14.
 */
public class BaseSync {

    public static int getLastIdInvoice(Context context) {
        Cursor curInv = context.getContentResolver().query(InvoiceContentProvider.CONTENT_URI, new String[] {"MAX(" + InvoiceBuyApiHolder.COL_ID +") AS id"}, null, null, null);

        curInv.moveToNext();

        int last_id = curInv.getInt(0);

        curInv.close();

        return last_id;
    }

    public static boolean sync(Context context) {
        return true;
    }
}
