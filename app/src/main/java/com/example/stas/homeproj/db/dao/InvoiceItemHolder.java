package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.InvoiceItem;

import java.text.ParseException;

/**
 * Created by user on 27.11.14.
 */
public class InvoiceItemHolder extends BaseSyncHolder {

    public final static String COL_COMMODITY_ID = "commodity_id";
    public final static String COL_PRICE_ID = "price_id";
    public final static String COL_FULLNAME = "fullname";
    public final static String COL_NUMBER_LOCAL = "number_local";
    public final static String COL_NUMBER_GLOBAL = "number_global";
    public final static String COL_FACTCOUNT = "factcount";
    public final static String COL_BARCODE = "barcode";

    public static void fromCursor(Cursor cur, InvoiceItem obj) {

        BaseSyncHolder.fromCursor(cur, obj);
        obj.commodity_id = cur.getInt(cur.getColumnIndex(COL_COMMODITY_ID));
        obj.price_id = cur.getInt(cur.getColumnIndex(COL_PRICE_ID));
        obj.fullname = cur.getString(cur.getColumnIndex(COL_FULLNAME));
        obj.number_local = cur.getString(cur.getColumnIndex(COL_NUMBER_LOCAL));
        obj.number_global = cur.getString(cur.getColumnIndex(COL_NUMBER_GLOBAL));
        obj.factcount = cur.getInt(cur.getColumnIndex(COL_FACTCOUNT));
        obj.barcode = cur.getInt(cur.getColumnIndex(COL_BARCODE));
    }

    public static ContentValues toCursor(InvoiceItem obj) {

        ContentValues cv = BaseSyncHolder.toCursor(obj);

        cv.put(COL_COMMODITY_ID, obj.commodity_id);
        cv.put(COL_PRICE_ID, obj.price_id);
        cv.put(COL_FULLNAME, obj.fullname);
        cv.put(COL_NUMBER_LOCAL, obj.number_local);
        cv.put(COL_NUMBER_GLOBAL, obj.number_global);
        cv.put(COL_FACTCOUNT, obj.factcount);
        cv.put(COL_BARCODE, obj.barcode);

        return cv;
    }
}
