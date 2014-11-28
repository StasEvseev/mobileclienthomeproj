package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stas.homeproj.db.dao.AcceptanceHolder;
import com.example.stas.homeproj.db.dao.CommodityHolder;
import com.example.stas.homeproj.db.dao.GoodHolder;
import com.example.stas.homeproj.db.dao.GoodLocalHolder;
import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;
import com.example.stas.homeproj.db.dao.InvoiceHolder;
import com.example.stas.homeproj.db.dao.InvoiceItemHolder;
import com.example.stas.homeproj.db.dao.PriceHolder;
import com.example.stas.homeproj.db.dao.ProviderHolder;
import com.example.stas.homeproj.db.dao.model.InvoiceItem;

/**
 * Created by user on 19.11.14.
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String LOG = DBHelper.class.getName();

    private static final String DATABASE_NAME = "homeproj.db";
    private static final int DATABASE_VERSION = 29;

    public final static String INVOICE_TABLE = "invoice";
    public final static String INVOICEITEM_TABLE = "invoice_item";
    public final static String ACCEPTANCE_TABLE = "acceptance";
    public final static String PROVIDER_TABLE = "provider";
    public final static String COMMODITY_TABLE = "commodity";
    public final static String PRICE_TABLE = "price";

    private final static String INVOICE_TABLE_CREATE = "create table "
            + INVOICE_TABLE + "(" +
            InvoiceHolder.COL_ID + " integer   primary key autoincrement, " +
            InvoiceHolder.COL_ID_BUY_API + " integer, " +
            InvoiceHolder.COL_IS_SYNC + " integer, " +
            InvoiceHolder.COL_DATE + " text, " +
            InvoiceHolder.COL_NUMBER + " text, " +
            InvoiceHolder.COL_PROVIDER_ID + " integer " +
            ");";

    private final static String INVOICEITEM_TABLE_CREATE = "create table "
            + INVOICEITEM_TABLE + "(" +
            InvoiceItemHolder.COL_ID + " integer   primary key autoincrement, " +
            InvoiceItemHolder.COL_ID_BUY_API + " integer, " +
            InvoiceItemHolder.COL_IS_SYNC + " integer, " +
            InvoiceItemHolder.COL_COMMODITY_ID + " integer, " +
            InvoiceItemHolder.COL_PRICE_ID + " integer, " +
            InvoiceItemHolder.COL_FULLNAME + " text, " +
            InvoiceItemHolder.COL_NUMBER_LOCAL + " text, " +
            InvoiceItemHolder.COL_NUMBER_GLOBAL + " text, " +
            InvoiceItemHolder.COL_FACTCOUNT + " integer, " +
            InvoiceItemHolder.COL_BARCODE + " integer, " +
            InvoiceItemHolder.COL_INVOICE_ID + " integer " +
            ");";

    private final static String ACCEPTANCE_TABLE_CREATE = "create table "
            + ACCEPTANCE_TABLE + "(" +
            AcceptanceHolder.COL_ID + " integer   primary key autoincrement, " +
            AcceptanceHolder.COL_ID_BUY_API + " integer, " +
            AcceptanceHolder.COL_IS_SYNC + " integer, " +
            AcceptanceHolder.COL_INVOICE_ID + " integer, " +
            AcceptanceHolder.COL_DATE + " text " +
            ");";

    private final static String PROVIDER_TABLE_CREATE = "create table "
            + PROVIDER_TABLE + "(" +
            ProviderHolder.COL_ID + " integer   primary key autoincrement, " +
            ProviderHolder.COL_ID_BUY_API + " integer, " +
            ProviderHolder.COL_IS_SYNC + " integer, " +
            ProviderHolder.COL_NAME + " text, " +
            ProviderHolder.COL_ADDRESS + " text " +
            ");";

    private final static String COMMODITY_TABLE_CREATE = "create table "
            + COMMODITY_TABLE + "(" +
            CommodityHolder.COL_ID + " integer   primary key autoincrement, " +
            CommodityHolder.COL_ID_BUY_API + " integer, " +
            CommodityHolder.COL_IS_SYNC + " integer, " +
            CommodityHolder.COL_NAME + " text, " +
            CommodityHolder.COL_CATEGORY + " text " +
            ");";

    private final static String PRICE_TABLE_CREATE = "create table "
            + PRICE_TABLE + "(" +
            PriceHolder.COL_ID + " integer   primary key autoincrement, " +
            PriceHolder.COL_ID_BUY_API + " integer, " +
            PriceHolder.COL_IS_SYNC + " integer, " +
            PriceHolder.COL_COMMODITY_ID + " integer, " +
            PriceHolder.COL_NDS + " real, " +
            PriceHolder.COL_PRICE_PREV + " real, " +
            PriceHolder.COL_PRICE_POST + " real, " +
            PriceHolder.COL_NUMBER_LOCAL + " text, " +
            PriceHolder.COL_NUMBER_GLOBAL + " text, " +
            PriceHolder.COL_PRICE_GROSS + " real, " +
            PriceHolder.COL_PRICE_RETAIL + " real, " +
            PriceHolder.COL_DATE_FROM + " text " +
            ");";

//    =================================================================
//
//
    public final static String INVOICE_TABLE_NAME = "invoice_buy_api";
    public final static String GOODBUY_TABLE_NAME = "good_buy_api";
    public final static String GOODLOCAL_TABLE_NAME = "good_local";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(LOG, "onCreate Table - " + INVOICE_TABLE + ", " + INVOICEITEM_TABLE + ", " + ACCEPTANCE_TABLE + ", "+ PROVIDER_TABLE + ", " + COMMODITY_TABLE + ", " + PRICE_TABLE);
        database.execSQL(INVOICE_TABLE_CREATE);
        database.execSQL(INVOICEITEM_TABLE_CREATE);
        database.execSQL(ACCEPTANCE_TABLE_CREATE);
        database.execSQL(PROVIDER_TABLE_CREATE);
        database.execSQL(COMMODITY_TABLE_CREATE);
        database.execSQL(PRICE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + INVOICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GOODBUY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GOODLOCAL_TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + INVOICE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INVOICEITEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACCEPTANCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROVIDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMMODITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRICE_TABLE);

        onCreate(db);
    }
}
