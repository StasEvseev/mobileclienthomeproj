package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.db.dao.model.Provider;

/**
 * Created by user on 27.11.14.
 */
public class ProviderHolder extends BaseSyncHolder {

    public final static String COL_NAME = "name";
    public final static String COL_ADDRESS = "address";

    public static void fromCursor(Cursor cursor, Provider obj) {
        BaseSyncHolder.fromCursor(cursor, obj);

        obj.name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        obj.address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS));
    }

    public static ContentValues toCursor(Provider obj) {
        ContentValues cv = BaseSyncHolder.toCursor(obj);
        cv.put(COL_NAME, obj.name);
        cv.put(COL_ADDRESS, obj.address);

        return cv;
    }
}
