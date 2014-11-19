package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stas.homeproj.db.dao.GoodBuyApiHolder;

/**
 * Created by user on 18.11.14.
 */
public class GoodBuyApiDBHelper extends BaseDBHelper {
//    public static final String DATABASE_NAME = "homeproj.db";
//    public static final int DATABASE_VERSION = 1;

    private static String LOG_TAG = GoodBuyApiDBHelper.class.getName();

    // DB Table consts
    public static final String TABLE_NAME = "good_buy_api";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +
            GoodBuyApiHolder.COL_ID + " integer   primary key autoincrement, " +
            GoodBuyApiHolder.COL_INVOICE_ID + " integer, " +
            GoodBuyApiHolder.COL_NAME + " text not null, " +
            GoodBuyApiHolder.COL_FULL_NAME + " text not null, " +
            GoodBuyApiHolder.COL_COUNT + " integer, " +
            GoodBuyApiHolder.COL_COUNT_ORDER + " integer, " +
            GoodBuyApiHolder.COL_COUNT_POSTORDER + " integer, " +
            GoodBuyApiHolder.COL_COUNT_WHOLE_PACK + " integer, " +
            GoodBuyApiHolder.COL_NUMBER_GLOBAL + " text, " +
            GoodBuyApiHolder.COL_NUMBER_LOCAL + " text, " +
            GoodBuyApiHolder.COL_PLACER + " integer, " +
            GoodBuyApiHolder.COL_PRICE_WITH_NDS + " real, " +
            GoodBuyApiHolder.COL_PRICE_WITHOUT_NDS + " real, " +
            GoodBuyApiHolder.COL_RATE_NDS + " real, " +
            GoodBuyApiHolder.COL_SUM_NDS + " real, " +
            GoodBuyApiHolder.COL_SUM_WITH_NDS + " real, " +
            GoodBuyApiHolder.COL_SUM_WITHOUT_NDS + " real, " +
            GoodBuyApiHolder.COL_THEMATIC + " text, " +
            GoodBuyApiHolder.COL_GOOD_ID + " integer " +
            ");";

    public GoodBuyApiDBHelper(Context context) {
//        Log.d(LOG_TAG, "asd");
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(BaseDBHelper.class.getName(), "onCreate Table" + TABLE_NAME);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(BaseDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
