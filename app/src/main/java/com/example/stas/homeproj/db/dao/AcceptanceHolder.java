package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.Acceptance;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Stanislav on 27.11.2014.
 */
public class AcceptanceHolder extends BaseSyncHolder {

    public static final String COL_INVOICE_ID = "invoice_id";
    public static final String COL_DATE = "date";

    public static void fromCursor(Cursor cur, Acceptance obj) {
        BaseSyncHolder.fromCursor(cur, obj);
        try {
            obj.date = Helper.getFormatter().parse(cur.getString(cur.getColumnIndex(COL_DATE)));
        } catch (ParseException e) {
            obj.date = new Date();
        }
        obj.invoice_id = cur.getInt(cur.getColumnIndex(COL_INVOICE_ID));
    }

    public static ContentValues toCursor(Acceptance obj) {
        ContentValues cv = BaseSyncHolder.toCursor(obj);
        cv.put(COL_INVOICE_ID, obj.invoice_id);
        cv.put(COL_DATE, Helper.getFormatter().format(obj.date));
        return cv;
    }

}
