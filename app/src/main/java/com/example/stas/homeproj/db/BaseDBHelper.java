package com.example.stas.homeproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 19.11.14.
 */
public abstract class BaseDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "homeproj.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "";
    public static final String TABLE_NAME = "";

    public BaseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
