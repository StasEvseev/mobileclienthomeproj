package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.Commodity;
import com.example.stas.homeproj.db.dao.model.Provider;

/**
 * Created by user on 27.11.14.
 */
public class CommodityHolder extends BaseSyncHolder {
    public final static String COL_NAME = "name";
    public final static String COL_CATEGORY = "category";

    public static void fromCursor(Cursor cursor, Commodity obj) {
        BaseSyncHolder.fromCursor(cursor, obj);

        obj.name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        obj.category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
    }

    public static ContentValues toCursor(Commodity obj) {
        ContentValues cv = BaseSyncHolder.toCursor(obj);
        cv.put(COL_NAME, obj.name);
        cv.put(COL_CATEGORY, obj.category);

        return cv;
    }
}
