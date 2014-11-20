package com.example.stas.homeproj.sync.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

//import com.example.stas.homeproj.db.GoodBuyApiDBHelper;
import com.example.stas.homeproj.db.dao.GoodBuyApiHolder;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodsBuyApi;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoiceCountResult;
import com.example.stas.homeproj.provider.GoodContentProvider;
import com.example.stas.homeproj.resources.IInvoiceItemCountRestAPI;
import com.example.stas.homeproj.resources.IInvoiceItemRestAPI;
import com.example.stas.homeproj.sync.BaseSync;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 17.11.14.
 */
public class GoodsSync extends BaseSync {
    private static String TAG = GoodsSync.class.getName();
    public static synchronized boolean sync(final Context context, InvoiceBuyApi inv) {

        Log.d(TAG, "start");

        final GoodBuyApiHolder holder = new GoodBuyApiHolder();

        Cursor curGood = context.getContentResolver().query(GoodContentProvider.CONTENT_URI, null,
                GoodBuyApiHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)}, null);

        curGood.moveToNext();
        int countLocal = curGood.getCount();

        IInvoiceItemCountRestAPI restCount = RestApiHelper.createResource(IInvoiceItemCountRestAPI.class, context);

        InvoiceCountResult result = restCount.count(inv.id);
        int countRemote = result.result.count;

        Log.d(TAG, "REMOTE - " + countRemote + " LOCAL - " + countLocal);

        context.getContentResolver().delete(GoodContentProvider.CONTENT_URI,
                GoodBuyApiHolder.COL_INVOICE_ID + "= ?", new String[] {String.valueOf(inv.id)});

        IInvoiceItemRestAPI restItems = RestApiHelper.createResource(IInvoiceItemRestAPI.class, context);

        restItems.goods(inv.id, new Callback<GoodsBuyApi>() {
            @Override
            public void success(GoodsBuyApi goodsBuyApi, Response response) {
                for(GoodBuyApi good: goodsBuyApi.items) {
                    context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, holder.toCursor(good));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

//        restItems.goods();

//        GoodBuyApiHolder holder = new GoodBuyApiHolder();
//        GoodBuyApi good;
//
//        if(curGood!= null) {
//            while(curGood.moveToNext()) {
//
//                good = holder.fromCursor(curGood);
//
////                Log.d(SyncAdapter.class.getName(),
////                        "ID" + curGood.getInt(curGood.getColumnIndex(GoodBuyApiHolder.COL_ID)) +
////                                "NAME" + curGood.getString(curGood.getColumnIndex(GoodBuyApiHolder.COL_NAME)));
//            }
//        }
//


//        GoodBuyApi good = new GoodBuyApi();
//        good.id = 1;
//        good.name = "Test";
//        good.full_name = "Test №123";
//        context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, new GoodBuyApiHolder().toCursor(good));
//        good.id = 2;
//        good.name = "Test 2";
//        good.full_name = "Test 2 №123";
//        context.getContentResolver().insert(GoodContentProvider.CONTENT_URI, new GoodBuyApiHolder().toCursor(good));
//
////        Cursor curGood = context.getContentResolver().query(GoodContentProvider.CONTENT_URI, null, null, null, null);
//
//        if(curGood!= null) {
//            while(curGood.moveToNext()) {
//                Log.d(SyncAdapter.class.getName(),
//                        "ID" + curGood.getInt(curGood.getColumnIndex(GoodBuyApiHolder.COL_ID)) +
//                                "NAME" + curGood.getString(curGood.getColumnIndex(GoodBuyApiHolder.COL_NAME)));
//            }
//        }
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
