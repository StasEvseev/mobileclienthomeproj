package com.example.stas.homeproj.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.stas.homeproj.MyApplication;
import com.example.stas.homeproj.db.DBHelper;
//import com.example.stas.homeproj.db.dao.GoodLocalHolder;
//import com.example.stas.homeproj.sync.AccountSyncHelper;

/**
 * @author StasEvseev
 * Провайдер для работы с товаром хранящимся в локальной базе.
 */
//public class GoodLocalContentProvider extends ContentProvider {
//    public static String LOG_TAG = GoodLocalContentProvider.class.getName();
//
//    public static String PATH = "good_local";
//    public static String AUTHORITY = MyApplication.AUTHORITY + '.' + PATH;
//    public static String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY;
//    public static String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY;
//
//    public static final Uri CONTENT_URI = Uri.parse("content://"
//            + AUTHORITY + "/" + PATH);
//
//    //// UriMatcher
//    // общий Uri
//    static final int URI_LOCALGOODS = 1;
//
//    // Uri с указанным ID
//    static final int URI_LOCALGOOD_BY_ID = 2;
//    // описание и создание UriMatcher
//    private static final UriMatcher uriMatcher;
//    static {
//        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(AUTHORITY, PATH, URI_LOCALGOODS);
//        uriMatcher.addURI(AUTHORITY, PATH + "/#", URI_LOCALGOOD_BY_ID);
//    }
//
//    DBHelper dbHelper;
//
//    @Override
//    public boolean onCreate() {
//        dbHelper = new DBHelper(getContext());
//        return true;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        Log.d(LOG_TAG, "query, " + uri.toString());
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        final int match = uriMatcher.match(uri);
//        switch (match) {
//            // retrieve tv shows list
//            case URI_LOCALGOODS: {
//                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//                builder.setTables(DBHelper.GOODLOCAL_TABLE_NAME);
//                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
//            }
//            case URI_LOCALGOOD_BY_ID: {
//                int id = (int) ContentUris.parseId(uri);
//                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//                builder.setTables(DBHelper.GOODLOCAL_TABLE_NAME);
//                builder.appendWhere(GoodLocalHolder.COL_ID + "=" + id);
//                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
//            }
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        Log.d(LOG_TAG, "getType, " + uri.toString());
//        switch (uriMatcher.match(uri)) {
//            case URI_LOCALGOODS:
//                return CONTENT_TYPE;
//            case URI_LOCALGOOD_BY_ID:
//                return CONTENT_TYPE_ITEM;
//        }
//        return null;
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        Log.d(LOG_TAG, "insert, " + uri.toString() + values.toString());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int token = uriMatcher.match(uri);
//        switch (token) {
//            case URI_LOCALGOODS: {
//                long id = db.insert(DBHelper.GOODLOCAL_TABLE_NAME, null, values);
//                if (id != -1)
//                    getContext().getContentResolver().notifyChange(uri, null);
//                return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
//            }
//            default: {
//                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
//            }
//        }
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int token = uriMatcher.match(uri);
//        int rowsDeleted = -1;
//        switch (token) {
//            case (URI_LOCALGOODS):
//                rowsDeleted = db.delete(DBHelper.GOODLOCAL_TABLE_NAME, selection, selectionArgs);
//                break;
//            case (URI_LOCALGOOD_BY_ID):
//                String tvShowIdWhereClause = GoodLocalHolder.COL_ID + "=" + uri.getLastPathSegment();
//                if (!TextUtils.isEmpty(selection))
//                    tvShowIdWhereClause += " AND " + selection;
//                rowsDeleted = db.delete(DBHelper.GOODLOCAL_TABLE_NAME, tvShowIdWhereClause, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported URI: " + uri);
//        }
//        // Notifying the changes, if there are any
//        if (rowsDeleted != -1)
//            getContext().getContentResolver().notifyChange(uri, null);
//        return rowsDeleted;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int token = uriMatcher.match(uri);
//
//        int rowsUpdated = -1;
//        switch (token) {
//            case (URI_LOCALGOODS):
//                rowsUpdated = db.update(DBHelper.GOODLOCAL_TABLE_NAME, values, selection, selectionArgs);
//                break;
//            case (URI_LOCALGOOD_BY_ID):
//                String tvShowIdWhereClause = GoodLocalHolder.COL_ID + "=" + uri.getLastPathSegment();
//                if (!TextUtils.isEmpty(selection))
//                    tvShowIdWhereClause += " AND " + selection;
//                rowsUpdated = db.update(DBHelper.GOODLOCAL_TABLE_NAME, values, tvShowIdWhereClause, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported URI: " + uri);
//        }
//        // Notifying the changes, if there are any
//        if (rowsUpdated != -1)
//            getContext().getContentResolver().notifyChange(uri, null);
//        return rowsUpdated;
//    }
//}
