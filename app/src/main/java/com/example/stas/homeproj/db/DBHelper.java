package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stas.homeproj.db.dao.GoodHolder;
import com.example.stas.homeproj.db.dao.GoodLocalHolder;
import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;

/**
 * Created by user on 19.11.14.
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String LOG = DBHelper.class.getName();

    private static final String DATABASE_NAME = "homeproj.db";
    private static final int DATABASE_VERSION = 22;

    public final static String INVOICE_TABLE_NAME = "invoice_buy_api";

    private final static String INVOICE_DATABASE_CREATE = "create table "
            + INVOICE_TABLE_NAME + "(" +
            InvoiceBuyApiHolder.COL_ID + " integer   primary key autoincrement, " +
            InvoiceBuyApiHolder.COL_NUMBER + " text, " +
            InvoiceBuyApiHolder.COL_DATE + " integer " +
            ");";

    public final static String GOODBUY_TABLE_NAME = "good_buy_api";

    private final static String GOODBUY_DATABASE_CREATE = "create table "
            + GOODBUY_TABLE_NAME + "(" +
            GoodHolder.COL_ID + " integer   primary key autoincrement, " +
            GoodHolder.COL_INVOICE_ID + " integer, " +
            GoodHolder.COL_NAME + " text not null, " +
            GoodHolder.COL_FULL_NAME + " text not null, " +
            GoodHolder.COL_COUNT + " integer, " +
            GoodHolder.COL_COUNT_ORDER + " integer, " +
            GoodHolder.COL_COUNT_POSTORDER + " integer, " +
            GoodHolder.COL_COUNT_WHOLE_PACK + " integer, " +
            GoodHolder.COL_NUMBER_GLOBAL + " text, " +
            GoodHolder.COL_NUMBER_LOCAL + " text, " +
            GoodHolder.COL_PLACER + " integer, " +
            GoodHolder.COL_PRICE_WITH_NDS + " real, " +
            GoodHolder.COL_PRICE_WITHOUT_NDS + " real, " +
            GoodHolder.COL_RATE_NDS + " real, " +
            GoodHolder.COL_SUM_NDS + " real, " +
            GoodHolder.COL_SUM_WITH_NDS + " real, " +
            GoodHolder.COL_SUM_WITHOUT_NDS + " real, " +
            GoodHolder.COL_THEMATIC + " text, " +
            GoodHolder.COL_GOOD_ID + " integer " +
            ");";

    public final static String GOODLOCAL_TABLE_NAME = "good_local";

    private final static String GOODLOCAL_DATABASE_CREATE = "create table "
            + GOODLOCAL_TABLE_NAME + "(" +
            GoodLocalHolder.COL_ID + " integer   primary key autoincrement, " +
            GoodLocalHolder.COL_FACT_COUNT + " integer, " +
            GoodLocalHolder.COL_BARCODE + " integer, " +
            GoodLocalHolder.COL_GOOD_ID + " integer, " +
            GoodLocalHolder.COL_SYNC + " BOOL " +
            ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(LOG, "onCreate Table - " + INVOICE_TABLE_NAME + ", " + GOODBUY_TABLE_NAME + ", " + GOODLOCAL_TABLE_NAME);
        database.execSQL(INVOICE_DATABASE_CREATE);
        database.execSQL(GOODBUY_DATABASE_CREATE);
        database.execSQL(GOODLOCAL_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + INVOICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GOODBUY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GOODLOCAL_TABLE_NAME);
        onCreate(db);
    }
}
