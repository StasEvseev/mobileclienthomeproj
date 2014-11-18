package com.example.stas.homeproj.sync;

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

import com.example.stas.homeproj.db.GoodDBHelper;

/**
 * Created by user on 18.11.14.
 */
public class GoodContentProvider extends ContentProvider {

    public static String LOG_TAG = GoodContentProvider.class.getName();

//    // Таблица
//    static final String TABLE = "goods";
//
//    // Поля
//    static final String CONTACT_ID = "_id";
//    static final String CONTACT_NAME = "name";
//    static final String CONTACT_EMAIL = "email";

    public static String PATH = "good";
    public static String AUTHORITY = AccountSyncHelper.AUTHORITY;
    public static String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + PATH;
    public static String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + PATH;

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + PATH);

    //// UriMatcher
    // общий Uri
    static final int URI_CONTACTS = 1;

    // Uri с указанным ID
    static final int URI_CONTACTS_ID = 2;
    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, URI_CONTACTS);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", URI_CONTACTS_ID);
    }

    private GoodDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new GoodDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            // retrieve tv shows list
            case URI_CONTACTS: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GoodDBHelper.TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_CONTACTS_ID: {
                int tvShowId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GoodDBHelper.TABLE_NAME);
                builder.appendWhere(GoodDBHelper.COL_ID + "=" + tvShowId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CONTACTS:
                return CONTENT_TYPE;
            case URI_CONTACTS_ID:
                return CONTENT_TYPE_ITEM;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString() + values.toString());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = uriMatcher.match(uri);
        switch (token) {
            case URI_CONTACTS: {
                long id = db.insert(GoodDBHelper.TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = uriMatcher.match(uri);
        int rowsDeleted = -1;
        switch (token) {
            case (URI_CONTACTS):
                rowsDeleted = db.delete(GoodDBHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case (URI_CONTACTS_ID):
                String tvShowIdWhereClause = GoodDBHelper.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GoodDBHelper.TABLE_NAME, tvShowIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
