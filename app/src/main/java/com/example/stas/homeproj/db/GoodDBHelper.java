package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 18.11.14.
 */
public class GoodDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "homeproj.db";
    private static final int DATABASE_VERSION = 1;

    // DB Table consts
    public static final String TABLE_NAME = "goods";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_YEAR = "year";


    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +
            COL_ID + " integer   primary key autoincrement, " +
            COL_NAME + " text not null, " +
            COL_YEAR + " integer " +
            ");";


    public GoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(GoodDBHelper.class.getName(), "onCreate");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(GoodDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
