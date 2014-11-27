package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.Price;
import java.text.ParseException;

/**
 * Created by user on 27.11.14.
 */
public class PriceHolder extends BaseSyncHolder {

    public final static String COL_COMMODITY_ID = "commodity_id";
    public final static String COL_NDS = "NDS";
    public final static String COL_PRICE_PREV = "price_prev";
    public final static String COL_PRICE_POST = "price_post";
    public final static String COL_NUMBER_LOCAL = "number_local";
    public final static String COL_NUMBER_GLOBAL = "number_global";
    public final static String COL_PRICE_GROSS = "price_gross";
    public final static String COL_PRICE_RETAIL = "price_retail";
    public final static String COL_DATE_FROM = "date_from";

    public static void fromCursor(Cursor cursor, Price obj) {
        BaseSyncHolder.fromCursor(cursor, obj);

        obj.commodity_id = cursor.getInt(cursor.getColumnIndex(COL_COMMODITY_ID));
        obj.NDS = cursor.getDouble(cursor.getColumnIndex(COL_NDS));
        obj.price_prev = cursor.getDouble(cursor.getColumnIndex(COL_PRICE_PREV));
        obj.price_post = cursor.getDouble(cursor.getColumnIndex(COL_PRICE_POST));
        obj.number_local = cursor.getString(cursor.getColumnIndex(COL_NUMBER_LOCAL));
        obj.number_global = cursor.getString(cursor.getColumnIndex(COL_NUMBER_GLOBAL));
        obj.price_gross = cursor.getDouble(cursor.getColumnIndex(COL_PRICE_GROSS));
        obj.price_retail = cursor.getDouble(cursor.getColumnIndex(COL_PRICE_RETAIL));
        try {
            obj.date_from = Helper.getFormatter().parse(cursor.getString(cursor.getColumnIndex(COL_DATE_FROM)));
        } catch (ParseException e) {

        }
    }

    public static ContentValues toCursor(Price obj) {
        ContentValues cv = BaseSyncHolder.toCursor(obj);
        cv.put(COL_COMMODITY_ID, obj.commodity_id);
        cv.put(COL_NDS, obj.NDS);
        cv.put(COL_PRICE_PREV, obj.price_prev);
        cv.put(COL_PRICE_POST, obj.price_post);
        cv.put(COL_NUMBER_LOCAL, obj.number_local);
        cv.put(COL_NUMBER_GLOBAL, obj.number_global);
        cv.put(COL_PRICE_GROSS, obj.price_gross);
        cv.put(COL_PRICE_RETAIL, obj.price_retail);
        if (obj.date_from != null) {
            cv.put(COL_DATE_FROM, Helper.getFormatter().format(obj.date_from));
        }
        return cv;
    }


}
