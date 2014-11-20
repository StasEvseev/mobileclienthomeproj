package com.example.stas.homeproj.sync;

import android.content.Context;
import android.database.Cursor;

import com.example.stas.homeproj.provider.InvoiceContentProvider;

/**
 * Created by user on 20.11.14.
 */
public class BaseSync {

    public static int getLastIdInvoice(Context context) {
        Cursor curInv = context.getContentResolver().query(InvoiceContentProvider.CONTENT_URI, new String[] {"MAX(id) AS id"}, null, null, null);

        curInv.moveToNext();

        return curInv.getInt(0);
    }

    public static boolean sync(Context context) {
        return true;
    }
}
