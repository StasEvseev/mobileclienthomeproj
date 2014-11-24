package com.example.stas.homeproj.sync.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.db.dao.GoodHolder;
import com.example.stas.homeproj.db.dao.GoodLocalHolder;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.provider.GoodContentProvider;
import com.example.stas.homeproj.provider.GoodLocalContentProvider;
import com.example.stas.homeproj.provider.helper.Provider;
import com.example.stas.homeproj.resources.IGoodRestAPI;

/**
 * Created by user on 24.11.14.
 */
public class GoodLocalSync {
    public final static String TAG = GoodLocalSync.class.getName();

    public static boolean sync(final Context context) {

        Log.d(TAG, "Sync start");

        IGoodRestAPI goodapi = RestApiHelper.createResource(IGoodRestAPI.class, context);

        Cursor cur = context.getContentResolver().query(GoodLocalContentProvider.CONTENT_URI, null,
                GoodLocalHolder.COL_SYNC + "=?", new String[] { "0" }, null);

        if(cur!=null) {
            while (cur.moveToNext()) {
                GoodLocal goodLocal = GoodLocalHolder.fromCursor(cur);
                if (goodLocal.id_good_buy_api != 0) {
                    GoodBuyApi goodBuyApi = (GoodBuyApi)Provider.getById(context, GoodContentProvider.CONTENT_URI,
                            GoodHolder.COL_ID, goodLocal.id_good_buy_api, GoodBuyApi.class, GoodHolder.class);
                    if(goodBuyApi.good_id != 0) {
                        goodapi.save(goodBuyApi.good_id, goodLocal);
                    }
                }
            }
        }

        cur.close();

        Log.d(TAG, "Sync end");

        return true;
    }
}
