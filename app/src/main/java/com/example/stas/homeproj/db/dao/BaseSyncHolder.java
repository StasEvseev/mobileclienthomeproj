package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.stas.homeproj.db.dao.model.BaseSyncModel;
import com.example.stas.homeproj.sync.model.BaseSync;

/**
 * Created by user on 27.11.14.
 */
public class BaseSyncHolder {
    public static final String COL_ID = "_id";
    public static final String COL_ID_BUY_API = "id_buy_api";
    public static final String COL_IS_SYNC = "is_sync";

    public static void fromCursor(Cursor cur, BaseSyncModel obj) {
        obj.id_buy_api = cur.getInt(cur.getColumnIndex(COL_ID_BUY_API));
        obj.id = cur.getInt(cur.getColumnIndex(COL_ID));

        int is_sync = cur.getInt(cur.getColumnIndex(COL_IS_SYNC));
        if (is_sync == 0) {
            obj.is_sync = false;
        } else if (is_sync == 1) {
            obj.is_sync = true;
        } else {
            Log.e("ERROR", "FUCKING BOOLEAN IN SQLITE");
        }
    }

    public static ContentValues toCursor(BaseSyncModel obj) {

        ContentValues cv = new ContentValues();
//        cv.put(COL_ID, obj.id);

        if (obj.id != 0) {
            cv.put(COL_ID, obj.id);
        }

        cv.put(COL_ID_BUY_API, obj.id_buy_api);
        if (obj.is_sync) {
            cv.put(COL_IS_SYNC, 1);
        } else {
            cv.put(COL_IS_SYNC, 0);
        }

        return cv;
    }


}
