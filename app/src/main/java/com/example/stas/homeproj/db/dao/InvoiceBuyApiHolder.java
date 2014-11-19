package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.models.InvoiceBuyApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 18.11.14.
 */
public class InvoiceBuyApiHolder implements BaseHolder<InvoiceBuyApi> {
    public static final String COL_ID = "id";
    public static final String COL_NUMBER = "number";
    public static final String COL_DATE = "date";

    private static SimpleDateFormat formatter;

    public static SimpleDateFormat getFormatter() {
        if (formatter == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        return formatter;
    }

    public InvoiceBuyApi fromCursor(Cursor cur) {
        InvoiceBuyApi invoice = new InvoiceBuyApi();
        invoice.id = cur.getInt(cur.getColumnIndex(COL_ID));
        try {
            invoice.date = getFormatter().parse(cur.getString(cur.getColumnIndex(COL_DATE)));
        } catch (ParseException e) {
            invoice.date = new Date();
        }

        invoice.number = cur.getString(cur.getColumnIndex(COL_NUMBER));

        return invoice;
    }

    public ContentValues toCursor(InvoiceBuyApi invoice) {
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, invoice.id);
        cv.put(COL_NUMBER, invoice.number);
        cv.put(COL_DATE, getFormatter().format(invoice.date));
        return cv;
    }
}
