package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.models.GoodBuyApi;

/**
 * Created by user on 18.11.14.
 */
public class GoodBuyApiHolder implements BaseHolder<GoodBuyApi>{
    public static String COL_COUNT = "count";
    public static String COL_COUNT_ORDER = "count_order";
    public static String COL_COUNT_POSTORDER = "count_postorder";
    public static String COL_COUNT_WHOLE_PACK = "count_whole_pack";
    public static String COL_FULL_NAME = "full_name";
    public static String COL_ID = "id";
    public static String COL_INVOICE_ID = "id_invoice";
    public static String COL_NAME = "name";
    public static String COL_NUMBER_GLOBAL = "number_global";
    public static String COL_NUMBER_LOCAL = "number_local";
    public static String COL_PLACER = "placer";
    public static String COL_PRICE_WITH_NDS = "price_with_NDS";
    public static String COL_PRICE_WITHOUT_NDS = "price_without_NDS";
    public static String COL_RATE_NDS = "rate_NDS";
    public static String COL_SUM_NDS = "sum_NDS";
    public static String COL_SUM_WITH_NDS = "sum_with_NDS";
    public static String COL_SUM_WITHOUT_NDS = "sum_without_NDS";
    public static String COL_THEMATIC = "thematic";
    public static String COL_GOOD_ID = "id_good";

    public GoodBuyApi fromCursor(Cursor cur) {
        GoodBuyApi good = new GoodBuyApi();
        good.id = cur.getInt(cur.getColumnIndex(COL_ID));
        good.invoice_id = cur.getInt(cur.getColumnIndex(COL_INVOICE_ID));
        good.count = cur.getInt(cur.getColumnIndex(COL_COUNT));
        good.count_order = cur.getInt(cur.getColumnIndex(COL_COUNT_ORDER));
        good.count_postorder = cur.getInt(cur.getColumnIndex(COL_COUNT_POSTORDER));
        good.count_whole_pack = cur.getInt(cur.getColumnIndex(COL_COUNT_WHOLE_PACK));
        good.full_name = cur.getString(cur.getColumnIndex(COL_FULL_NAME));
        good.name = cur.getString(cur.getColumnIndex(COL_NAME));
        good.number_global = cur.getString(cur.getColumnIndex(COL_NUMBER_GLOBAL));
        good.number_local = cur.getString(cur.getColumnIndex(COL_NUMBER_LOCAL));
        good.placer = cur.getInt(cur.getColumnIndex(COL_PLACER));
        good.price_with_NDS = cur.getDouble(cur.getColumnIndex(COL_PRICE_WITH_NDS));
        good.price_without_NDS = cur.getDouble(cur.getColumnIndex(COL_PRICE_WITHOUT_NDS));
        good.rate_NDS = cur.getDouble(cur.getColumnIndex(COL_RATE_NDS));
        good.sum_NDS = cur.getDouble(cur.getColumnIndex(COL_SUM_NDS));
        good.sum_with_NDS = cur.getDouble(cur.getColumnIndex(COL_SUM_WITH_NDS));
        good.sum_without_NDS = cur.getDouble(cur.getColumnIndex(COL_SUM_WITHOUT_NDS));
        good.thematic = cur.getString(cur.getColumnIndex(COL_THEMATIC));
        good.good_id = cur.getInt(cur.getColumnIndex(COL_GOOD_ID));

        return good;
    }

    public ContentValues toCursor(GoodBuyApi good) {

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, good.id);
        cv.put(COL_INVOICE_ID, good.invoice_id);
        cv.put(COL_COUNT, good.count);
        cv.put(COL_COUNT_ORDER, good.count_order);
        cv.put(COL_COUNT_POSTORDER, good.count_postorder);
        cv.put(COL_COUNT_WHOLE_PACK, good.count_whole_pack);
        cv.put(COL_FULL_NAME, good.full_name);
        cv.put(COL_NAME, good.name);
        cv.put(COL_NUMBER_GLOBAL, good.number_global);
        cv.put(COL_NUMBER_LOCAL, good.number_local);
        cv.put(COL_PLACER, good.placer);
        cv.put(COL_PRICE_WITH_NDS, good.price_with_NDS);
        cv.put(COL_PRICE_WITHOUT_NDS, good.price_without_NDS);
        cv.put(COL_RATE_NDS, good.rate_NDS);
        cv.put(COL_SUM_NDS, good.sum_NDS);
        cv.put(COL_SUM_WITH_NDS, good.sum_with_NDS);
        cv.put(COL_SUM_WITHOUT_NDS, good.sum_with_NDS);
        cv.put(COL_THEMATIC, good.thematic);
        cv.put(COL_GOOD_ID, good.good_id);

        return cv;
    }
}
