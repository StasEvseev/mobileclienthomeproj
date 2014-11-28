package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;

/**
 * @author StasEvseev
 * Класс для работы с GoodLocal в SQLite
 */
//public class GoodLocalHolder {
//
//    public static final String COL_ID = "_id";
//    public static final String COL_FACT_COUNT = "fact_count";
//    public static final String COL_BARCODE = "barcode";
//    public static final String COL_GOOD_ID = "id_good";
//    public static final String COL_SYNC = "is_sync";
//
//    public static GoodLocal fromCursor(Cursor cur) {
//
//        GoodLocal good = new GoodLocal();
//        good.id = cur.getInt(cur.getColumnIndex(COL_ID));
//        good.barcode = cur.getInt(cur.getColumnIndex(COL_BARCODE));
//        good.factCount = cur.getInt(cur.getColumnIndex(COL_FACT_COUNT));
//        good.id_good_buy_api = cur.getInt(cur.getColumnIndex(COL_GOOD_ID));
//
//        int is_sync = cur.getInt(cur.getColumnIndex(COL_SYNC));
//        if (is_sync == 0) {
//            good.is_sync = false;
//        } else if (is_sync == 1) {
//            good.is_sync = true;
//        } else {
//            Log.e("ERROR", "FUCKING BOOLEAN IN SQLITE");
//        }
//
//        return good;
//    }
//
//    public static ContentValues toCursor(GoodLocal goodl) {
//        ContentValues cv = new ContentValues();
//
//        if (goodl.id != 0) {
//            cv.put(COL_ID, goodl.id);
//        }
//        cv.put(COL_FACT_COUNT, goodl.factCount);
//        cv.put(COL_BARCODE, goodl.barcode);
//        cv.put(COL_GOOD_ID, goodl.good.id);
//
//        if (goodl.is_sync) {
//            cv.put(COL_SYNC, 1);
//        } else {
//            cv.put(COL_SYNC, 0);
//        }
//
//        return cv;
//    }
//
//}
