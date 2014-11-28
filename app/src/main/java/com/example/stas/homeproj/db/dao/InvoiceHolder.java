package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.Invoice;

import java.text.ParseException;


/**
 * Created by user on 27.11.14.
 */
public class InvoiceHolder extends BaseSyncHolder {

    public final static String COL_PROVIDER_ID = "provider_id";
    public final static String COL_NUMBER = "number";
    public final static String COL_DATE = "date";


    public static void fromCursor(Cursor cur, Invoice obj) {

        BaseSyncHolder.fromCursor(cur, obj);
        obj.provider_id = cur.getInt(cur.getColumnIndex(COL_PROVIDER_ID));
        obj.number = cur.getString(cur.getColumnIndex(COL_NUMBER));

        try {
            obj.date = Helper.getFormatter().parse(cur.getString(cur.getColumnIndex(COL_DATE)));
        } catch (ParseException e) {
        }
    }

    public static ContentValues toCursor(Invoice obj) {

        ContentValues cv = BaseSyncHolder.toCursor(obj);

        cv.put(COL_PROVIDER_ID, obj.provider_id);
        cv.put(COL_NUMBER, obj.number);

        if (obj.date != null) {
            cv.put(COL_DATE, Helper.getFormatter().format(obj.date));
        }

        return cv;
    }

}
