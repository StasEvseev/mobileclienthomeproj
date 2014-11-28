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
import com.example.stas.homeproj.db.dao.AcceptanceHolder;
import com.example.stas.homeproj.db.dao.CommodityHolder;
import com.example.stas.homeproj.db.dao.InvoiceHolder;
import com.example.stas.homeproj.db.dao.InvoiceItemHolder;
import com.example.stas.homeproj.db.dao.PriceHolder;
import com.example.stas.homeproj.db.dao.ProviderHolder;

/**
 * Created by user on 27.11.14.
 */
public class MainContentProvider extends ContentProvider{

    public static String LOG_TAG = MainContentProvider.class.getName();

    public static String PATH_INVOICE = "invoice";
    public static String PATH_INVOICEITEM = "invoiceitem";
    public static String PATH_ACCEPTANCE = "acceptance";
    public static String PATH_COMMODITY = "commodity";
    public static String PATH_PRICE = "price";
    public static String PATH_PROVIDER = "provider";

    public static String AUTHORITY = MyApplication.AUTHORITY + ".main";
    public static String CONTENT_TYPE_INVOICE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_INVOICE;
    public static String CONTENT_TYPE_INVOICE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_INVOICE;

    public static String CONTENT_TYPE_INVOICEITEM = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_INVOICEITEM;
    public static String CONTENT_TYPE_INVOICEITEM_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_INVOICEITEM;

    public static String CONTENT_TYPE_ACCEPTANCE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_ACCEPTANCE;
    public static String CONTENT_TYPE_ACCEPTANCE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_ACCEPTANCE;

    public static String CONTENT_TYPE_COMMODITY = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_COMMODITY;
    public static String CONTENT_TYPE_COMMODITY_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_COMMODITY;

    public static String CONTENT_TYPE_PRICE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_PRICE;
    public static String CONTENT_TYPE_PRICE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_PRICE;

    public static String CONTENT_TYPE_PROVIDER = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_PROVIDER;
    public static String CONTENT_TYPE_PROVIDER_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_PROVIDER;

    public static final Uri CONTENT_URI_INVOICE = Uri.parse("content://"
            + AUTHORITY + "/" + PATH_INVOICE);
    public static final Uri CONTENT_URI_INVOICEITEM = Uri.parse("content://"
            + AUTHORITY + "/" + PATH_INVOICEITEM);
    public static final Uri CONTENT_URI_ACCEPTANCE = Uri.parse("content://"
            + AUTHORITY + "/" + PATH_ACCEPTANCE);
    public static final Uri CONTENT_URI_COMMODITY = Uri.parse("content://"
            + AUTHORITY + "/" + PATH_COMMODITY);
    public static final Uri CONTENT_URI_PRICE = Uri.parse("content://"
            + AUTHORITY + "/" + PATH_PRICE);
    public static final Uri CONTENT_URI_PROVIDER = Uri.parse("content://"
            + AUTHORITY + "/" + PATH_PROVIDER);

    //// UriMatcher
    static final int URI_INVOICE = 1;
    static final int URI_INVOICE_BY_ID = 2;

    static final int URI_INVOICEITEM = 3;
    static final int URI_INVOICEITEM_BY_ID = 4;

    static final int URI_ACCEPTANCE = 5;
    static final int URI_ACCEPTANCE_BY_ID = 6;

    static final int URI_COMMODITY = 7;
    static final int URI_COMMODITY_BY_ID = 8;

    static final int URI_PRICE = 9;
    static final int URI_PRICE_BY_ID = 10;

    static final int URI_PROVIDER = 11;
    static final int URI_PROVIDER_BY_ID = 12;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH_INVOICE, URI_INVOICE);
        uriMatcher.addURI(AUTHORITY, PATH_INVOICE + "/#", URI_INVOICE_BY_ID);
        uriMatcher.addURI(AUTHORITY, PATH_INVOICEITEM, URI_INVOICEITEM);
        uriMatcher.addURI(AUTHORITY, PATH_INVOICEITEM + "/#", URI_INVOICEITEM_BY_ID);
        uriMatcher.addURI(AUTHORITY, PATH_ACCEPTANCE, URI_ACCEPTANCE);
        uriMatcher.addURI(AUTHORITY, PATH_ACCEPTANCE + "/#", URI_ACCEPTANCE_BY_ID);
        uriMatcher.addURI(AUTHORITY, PATH_COMMODITY, URI_COMMODITY);
        uriMatcher.addURI(AUTHORITY, PATH_COMMODITY + "/#", URI_COMMODITY_BY_ID);
        uriMatcher.addURI(AUTHORITY, PATH_PRICE, URI_PRICE);
        uriMatcher.addURI(AUTHORITY, PATH_PRICE + "/#", URI_PRICE_BY_ID);
        uriMatcher.addURI(AUTHORITY, PATH_PROVIDER, URI_PROVIDER);
        uriMatcher.addURI(AUTHORITY, PATH_PROVIDER + "/#", URI_PROVIDER_BY_ID);
    }

    DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_INVOICE:
                return CONTENT_TYPE_INVOICE;
            case URI_INVOICE_BY_ID:
                return CONTENT_TYPE_INVOICE_ITEM;
            case URI_INVOICEITEM:
                return CONTENT_TYPE_INVOICEITEM;
            case URI_INVOICEITEM_BY_ID:
                return CONTENT_TYPE_INVOICEITEM_ITEM;
            case URI_ACCEPTANCE:
                return CONTENT_TYPE_ACCEPTANCE;
            case URI_ACCEPTANCE_BY_ID:
                return CONTENT_TYPE_ACCEPTANCE_ITEM;
            case URI_COMMODITY:
                return CONTENT_TYPE_COMMODITY;
            case URI_COMMODITY_BY_ID:
                return CONTENT_TYPE_COMMODITY_ITEM;
            case URI_PRICE:
                return CONTENT_TYPE_PRICE;
            case URI_PRICE_BY_ID:
                return CONTENT_TYPE_PRICE_ITEM;
            case URI_PROVIDER:
                return CONTENT_TYPE_PROVIDER;
            case URI_PROVIDER_BY_ID:
                return CONTENT_TYPE_PROVIDER_ITEM;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            // retrieve tv shows list
            case URI_INVOICE: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.INVOICE_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_INVOICE_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.INVOICE_TABLE);
                builder.appendWhere(InvoiceHolder.COL_ID + "=" + id);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case URI_INVOICEITEM: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.INVOICEITEM_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_INVOICEITEM_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.INVOICEITEM_TABLE);
                builder.appendWhere(InvoiceItemHolder.COL_ID + "=" + id);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case URI_ACCEPTANCE: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.ACCEPTANCE_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_ACCEPTANCE_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.ACCEPTANCE_TABLE);
                builder.appendWhere(AcceptanceHolder.COL_ID + "=" + id);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case URI_COMMODITY: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.COMMODITY_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_COMMODITY_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.COMMODITY_TABLE);
                builder.appendWhere(CommodityHolder.COL_ID + "=" + id);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case URI_PRICE: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.PRICE_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_PRICE_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.PRICE_TABLE);
                builder.appendWhere(PriceHolder.COL_ID + "=" + id);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case URI_PROVIDER: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.PROVIDER_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case URI_PROVIDER_BY_ID: {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DBHelper.PROVIDER_TABLE);
                builder.appendWhere(ProviderHolder.COL_ID + "=" + id);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString() + values.toString());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = uriMatcher.match(uri);
        switch (token) {
            case URI_INVOICE: {
                long id = db.insert(DBHelper.INVOICE_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI_INVOICE.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case URI_INVOICEITEM: {
                long id = db.insert(DBHelper.INVOICEITEM_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI_INVOICEITEM.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case URI_ACCEPTANCE: {
                long id = db.insert(DBHelper.ACCEPTANCE_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI_ACCEPTANCE.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case URI_COMMODITY: {
                long id = db.insert(DBHelper.COMMODITY_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI_COMMODITY.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case URI_PRICE: {
                long id = db.insert(DBHelper.PRICE_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI_PRICE.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case URI_PROVIDER: {
                long id = db.insert(DBHelper.PROVIDER_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return CONTENT_URI_PROVIDER.buildUpon().appendPath(String.valueOf(id)).build();
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
        String tvShowIdWhereClause = "";
        switch (token) {
            case (URI_INVOICE):
                rowsDeleted = db.delete(DBHelper.INVOICE_TABLE, selection, selectionArgs);
                break;
            case (URI_INVOICE_BY_ID):
                tvShowIdWhereClause = InvoiceHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.INVOICE_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            case (URI_INVOICEITEM):
                rowsDeleted = db.delete(DBHelper.INVOICEITEM_TABLE, selection, selectionArgs);
                break;
            case (URI_INVOICEITEM_BY_ID):
                tvShowIdWhereClause = InvoiceItemHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.INVOICEITEM_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            case (URI_ACCEPTANCE):
                rowsDeleted = db.delete(DBHelper.ACCEPTANCE_TABLE, selection, selectionArgs);
                break;
            case (URI_ACCEPTANCE_BY_ID):
                tvShowIdWhereClause = AcceptanceHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.ACCEPTANCE_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            case (URI_COMMODITY):
                rowsDeleted = db.delete(DBHelper.COMMODITY_TABLE, selection, selectionArgs);
                break;
            case (URI_COMMODITY_BY_ID):
                tvShowIdWhereClause = CommodityHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.COMMODITY_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            case (URI_PRICE):
                rowsDeleted = db.delete(DBHelper.PRICE_TABLE, selection, selectionArgs);
                break;
            case (URI_PRICE_BY_ID):
                tvShowIdWhereClause = PriceHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.PRICE_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            case (URI_PROVIDER):
                rowsDeleted = db.delete(DBHelper.PROVIDER_TABLE, selection, selectionArgs);
                break;
            case (URI_PROVIDER_BY_ID):
                tvShowIdWhereClause = ProviderHolder.COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DBHelper.PROVIDER_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
//        Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = uriMatcher.match(uri);

        int rowsUpdated = -1;
        switch (token) {
//            case (URI_LOCALGOODS):
//                rowsUpdated = db.update(DBHelper.GOODLOCAL_TABLE_NAME, values, selection, selectionArgs);
//                break;
//            case (URI_LOCALGOOD_BY_ID):
//                String tvShowIdWhereClause = GoodLocalHolder.COL_ID + "=" + uri.getLastPathSegment();
//                if (!TextUtils.isEmpty(selection))
//                    tvShowIdWhereClause += " AND " + selection;
//                rowsUpdated = db.update(DBHelper.GOODLOCAL_TABLE_NAME, values, tvShowIdWhereClause, selectionArgs);
//                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
//        if (rowsUpdated != -1)
//            getContext().getContentResolver().notifyChange(uri, null);
//        return rowsUpdated;
    }

}
