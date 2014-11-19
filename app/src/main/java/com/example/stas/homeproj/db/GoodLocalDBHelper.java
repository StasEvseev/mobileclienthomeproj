package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stas.homeproj.db.dao.GoodBuyApiHolder;
import com.example.stas.homeproj.db.dao.GoodLocalHolder;

/**
 * Created by user on 19.11.14.
 */
public class GoodLocalDBHelper extends BaseDBHelper {

    public static final String TABLE_NAME = "good_local";
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +
            GoodLocalHolder.COL_ID + " integer   primary key autoincrement, " +
            GoodLocalHolder.COL_FACT_COUNT + " integer, " +
            GoodLocalHolder.COL_BARCODE + " integer " +
            ");";

    public GoodLocalDBHelper(Context context) {
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
