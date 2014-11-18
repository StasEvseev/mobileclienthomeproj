package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;

/**
 * Created by user on 18.11.14.
 */
public class GoodLocalHolder implements BaseHolder<GoodLocal> {

    public String COL_ID = "id";

//    public String COL_GOOD_BUY_ID = "id_good_buy";
    public String COL_FACT_COUNT = "fact_count";
    public String COL_BARCODE = "barcode";

    public GoodLocal fromCursor(Cursor cur) {

        GoodLocal good = new GoodLocal();
        good.id = cur.getInt(cur.getColumnIndex(COL_ID));
        good.barcode = cur.getInt(cur.getColumnIndex(COL_BARCODE));
        good.factCount = cur.getInt(cur.getColumnIndex(COL_FACT_COUNT));

        return good;
    }

    public ContentValues toCursor(GoodLocal goodl) {
        ContentValues cv = new ContentValues();

        cv.put(COL_ID, goodl.getId());
        cv.put(COL_FACT_COUNT, goodl.factCount);
        cv.put(COL_BARCODE, goodl.barcode);

        return cv;
    }

}
