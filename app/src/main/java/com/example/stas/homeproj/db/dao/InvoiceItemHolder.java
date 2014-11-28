package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.InvoiceItem;

/**
 * Created by user on 27.11.14.
 */
public class InvoiceItemHolder extends BaseSyncHolder {

    public final static String COL_INVOICE_ID = "invoice_id";
    public final static String COL_COMMODITY_ID = "commodity_id";
    public final static String COL_PRICE_ID = "price_id";
    public final static String COL_FULLNAME = "fullname";
    public final static String COL_NUMBER_LOCAL = "number_local";
    public final static String COL_NUMBER_GLOBAL = "number_global";
    public final static String COL_FACTCOUNT = "factcount";
    public final static String COL_BARCODE = "barcode";

    public final static String COL_COUNT = "count";
    public final static String COL_COUNT_WHOLE_PACK = "count_whole_pack";
    public final static String COL_PLACER = "placer";

    public static void fromCursor(Cursor cur, InvoiceItem obj) {

        BaseSyncHolder.fromCursor(cur, obj);
        obj.invoice_id = cur.getInt(cur.getColumnIndex(COL_INVOICE_ID));
        obj.commodity_id = cur.getInt(cur.getColumnIndex(COL_COMMODITY_ID));
        obj.price_id = cur.getInt(cur.getColumnIndex(COL_PRICE_ID));
        obj.fullname = cur.getString(cur.getColumnIndex(COL_FULLNAME));
        obj.number_local = cur.getString(cur.getColumnIndex(COL_NUMBER_LOCAL));
        obj.number_global = cur.getString(cur.getColumnIndex(COL_NUMBER_GLOBAL));
        obj.factcount = cur.getInt(cur.getColumnIndex(COL_FACTCOUNT));
        obj.barcode = cur.getInt(cur.getColumnIndex(COL_BARCODE));
        obj.count = cur.getInt(cur.getColumnIndex(COL_COUNT));
        obj.count_whole_pack = cur.getInt(cur.getColumnIndex(COL_COUNT_WHOLE_PACK));
        obj.placer = cur.getInt(cur.getColumnIndex(COL_PLACER));
    }

    public static ContentValues toCursor(InvoiceItem obj) {

        ContentValues cv = BaseSyncHolder.toCursor(obj);

        cv.put(COL_INVOICE_ID, obj.invoice_id);
        cv.put(COL_COMMODITY_ID, obj.commodity_id);
        cv.put(COL_PRICE_ID, obj.price_id);
        cv.put(COL_FULLNAME, obj.fullname);
        cv.put(COL_NUMBER_LOCAL, obj.number_local);
        cv.put(COL_NUMBER_GLOBAL, obj.number_global);
        cv.put(COL_FACTCOUNT, obj.factcount);
        cv.put(COL_BARCODE, obj.barcode);
        cv.put(COL_COUNT, obj.count);
        cv.put(COL_COUNT_WHOLE_PACK, obj.count_whole_pack);
        cv.put(COL_PLACER, obj.placer);

        return cv;
    }
}
