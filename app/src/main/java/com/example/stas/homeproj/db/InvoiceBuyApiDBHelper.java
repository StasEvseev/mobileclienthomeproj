package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;

/**
 * Created by user on 19.11.14.
 */
public class InvoiceBuyApiDBHelper extends BaseDBHelper {

    public static final String TABLE_NAME = "invoice_buy_api";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +
            InvoiceBuyApiHolder.COL_ID + " integer   primary key autoincrement, " +
            InvoiceBuyApiHolder.COL_NUMBER + " text, " +
            InvoiceBuyApiHolder.COL_DATE + " integer " +
            ");";

    public InvoiceBuyApiDBHelper(Context context) {
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
