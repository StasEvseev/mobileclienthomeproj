package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.stas.homeproj.models.InvoiceBuyApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 18.11.14.
 */
public class InvoiceBuyApiHolder {
    public static final String COL_ID = "_id";
    public static final String COL_NUMBER = "number";
    public static final String COL_DATE = "date";
    public static final String COL_IS_ACCEPTANCE = "is_acceptance";

    private static SimpleDateFormat formatter;

    public static SimpleDateFormat getFormatter() {
        if (formatter == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        return formatter;
    }

    public static InvoiceBuyApi fromCursor(Cursor cur) {
        InvoiceBuyApi invoice = new InvoiceBuyApi();
        invoice.id = cur.getInt(cur.getColumnIndex(COL_ID));
        try {
            invoice.date = getFormatter().parse(cur.getString(cur.getColumnIndex(COL_DATE)));
        } catch (ParseException e) {
            invoice.date = new Date();
        }

        invoice.number = cur.getString(cur.getColumnIndex(COL_NUMBER));
//        invoice.is_acceptance

        int is_sync = cur.getInt(cur.getColumnIndex(COL_IS_ACCEPTANCE));
        if (is_sync == 0) {
            invoice.is_acceptance = false;
        } else if (is_sync == 1) {
            invoice.is_acceptance = true;
        } else {
            Log.e("ERROR", "FUCKING BOOLEAN IN SQLITE");
        }

        return invoice;
    }

    public static ContentValues toCursor(InvoiceBuyApi invoice) {
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, invoice.id);
        cv.put(COL_NUMBER, invoice.number);
        cv.put(COL_DATE, getFormatter().format(invoice.date));

        if (invoice.is_acceptance) {
            cv.put(COL_IS_ACCEPTANCE, 1);
        } else {
            cv.put(COL_IS_ACCEPTANCE, 0);
        }

        return cv;
    }
}
