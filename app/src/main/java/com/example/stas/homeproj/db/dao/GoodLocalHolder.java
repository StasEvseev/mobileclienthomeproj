package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;

/**
 * @author StasEvseev
 * Класс для работы с GoodLocal в SQLite
 */
public class GoodLocalHolder {

    public static final String COL_ID = "_id";
    public static final String COL_FACT_COUNT = "fact_count";
    public static final String COL_BARCODE = "barcode";
    public static final String COL_GOOD_ID = "id_good";

    public static GoodLocal fromCursor(Cursor cur) {

        GoodLocal good = new GoodLocal();
        good.id = cur.getInt(cur.getColumnIndex(COL_ID));
        good.barcode = cur.getInt(cur.getColumnIndex(COL_BARCODE));
        good.factCount = cur.getInt(cur.getColumnIndex(COL_FACT_COUNT));
        good.id_good = cur.getInt(cur.getColumnIndex(COL_GOOD_ID));

        return good;
    }

    public static ContentValues toCursor(GoodLocal goodl) {
        ContentValues cv = new ContentValues();

        cv.put(COL_ID, goodl.id);
        cv.put(COL_FACT_COUNT, goodl.factCount);
        cv.put(COL_BARCODE, goodl.barcode);
        cv.put(COL_GOOD_ID, goodl.getId());

        return cv;
    }

}
