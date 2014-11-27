package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.db.dao.model.BaseSyncModel;
import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.sync.model.BaseSync;

import java.text.ParseException;
import java.util.Date;

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
//        cv.put(COL_DATE, '');

        if (obj.date != null) {
            cv.put(COL_DATE, Helper.getFormatter().format(obj.date));
        }

        return cv;
    }

}
