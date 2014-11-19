package com.example.stas.homeproj.sync;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.stas.homeproj.data.GoodContent;
import com.example.stas.homeproj.db.dao.GoodBuyApiHolder;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.Good;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.provider.GoodContentProvider;
import com.example.stas.homeproj.resources.IGoodRestAPI;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 17.11.14.
 */
public class GoodsSync {
    private static String TAG = GoodsSync.class.getName();
    public static synchronized boolean sync(Context context) {
        Log.d(TAG, "start");

        GoodBuyApi good = new GoodBuyApi();
        good.id = 1;
        good.name = "Test";
        good.full_name = "Test №123";
        context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, new GoodBuyApiHolder().toCursor(good));
        good.id = 2;
        good.name = "Test 2";
        good.full_name = "Test 2 №123";
        context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, new GoodBuyApiHolder().toCursor(good));

        Cursor curGood = context.getContentResolver().query(GoodContentProvider.CONTENT_URI, null, null, null, null);

        if(curGood!= null) {
            while(curGood.moveToNext()) {
                Log.d(SyncAdapter.class.getName(),
                        "ID" + curGood.getInt(curGood.getColumnIndex(GoodBuyApiHolder.COL_ID)) +
                                "NAME" + curGood.getString(curGood.getColumnIndex(GoodBuyApiHolder.COL_NAME)));
            }
        }
//
////        List<GoodLocal> gl = GoodContent.getItems();
//        IGoodRestAPI goodApi = RestApiHelper.createResource(IGoodRestAPI.class, context);
//        GoodContent.addItem(new GoodLocal(new GoodBuyApi()));
//        Log.d(TAG, "Count - " + String.valueOf(gl.size()));
//        for(int i = 0; i < gl.size(); i++) {
//
//            GoodLocal goodL = gl.get(i);
//
//            Good good = new Good();
//            good.full_name = goodL.getFullname();
//            good.count = goodL.getCount();
//            good.barcode = goodL.barcode;
//
//            goodApi.save(gl.get(i).getId(), good, new Callback<String>() {
//                @Override
//                public void success(String s, Response response) {
//                    Log.d(TAG, s);
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.d(TAG, error.toString());
//                }
//            });
//        }

        Log.d(TAG, "end");



        return true;
    }
}
