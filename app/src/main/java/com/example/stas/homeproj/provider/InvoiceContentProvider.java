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

import com.example.stas.homeproj.db.DBHelper;
import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;
import com.example.stas.homeproj.sync.AccountSyncHelper;

/**
 * Created by user on 19.11.14.
 */
public class InvoiceContentProvider extends ContentProvider {

    public static String LOG_TAG = InvoiceContentProvider.class.getName();

    public static String PATH = "invoice";
    public static String AUTHORITY = AccountSyncHelper.AUTHORITY + '.' + PATH;
    public static String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY;
    public static String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY;

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + PATH);

    //// UriMatcher
    // общий Uri
    static final int URI_INVOICES = 1;

    // Uri с указанным ID
    static final int URI_INVOICE_BY_ID = 2;
    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, URI_INVOICES);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", URI_INVOICE_BY_ID);
    }

    DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            // retrieve tv shows list
            case URI_INVOICES: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.INVOICE_TABLE_NAME);
                Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            case URI_INVOICE_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.INVOICE_TABLE_NAME);
                builder.appendWhere(InvoiceBuyApiHolder.COL_ID + "=" + id);
                Cursor cursor;
                cursor = builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_INVOICES:
                return CONTENT_TYPE;
            case URI_INVOICE_BY_ID:
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
            case URI_INVOICES: {
                long id = db.insert(DBHelper.INVOICE_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
//                onContentChanged(getContext(), SQLiteOperation.INSERT, extras);
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
            case (URI_INVOICES):
                rowsDeleted = db.delete(DBHelper.INVOICE_TABLE_NAME, selection, selectionArgs);
                break;
            case (URI_INVOICE_BY_ID):
                String tvShowIdWhereClause = InvoiceBuyApiHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.INVOICE_TABLE_NAME, tvShowIdWhereClause, selectionArgs);
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
