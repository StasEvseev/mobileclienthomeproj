package com.example.stas.homeproj.sync;

import android.content.Context;
import android.util.Log;

import com.example.stas.homeproj.data.GoodContent;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.Good;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.resources.IGoodRestAPI;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 17.11.14.
 */
public class GoodsSync {
    private static String TAG = "GoodSync";
    public static synchronized boolean sync(Context context, List<GoodLocal> gl) {
        Log.d(TAG, "start");

//        List<GoodLocal> gl = GoodContent.getItems();
        IGoodRestAPI goodApi = RestApiHelper.createResource(IGoodRestAPI.class, context);
        GoodContent.addItem(new GoodLocal(new GoodBuyApi()));
        Log.d(TAG, "Count - " + String.valueOf(gl.size()));
        for(int i = 0; i < gl.size(); i++) {

            GoodLocal goodL = gl.get(i);

            Good good = new Good();
            good.full_name = goodL.getFullname();
            good.count = goodL.getCount();
            good.barcode = goodL.barcode;

            goodApi.save(gl.get(i).getId(), good, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.d(TAG, s);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, error.toString());
                }
            });
        }

        Log.d(TAG, "end");



        return true;
    }
}
