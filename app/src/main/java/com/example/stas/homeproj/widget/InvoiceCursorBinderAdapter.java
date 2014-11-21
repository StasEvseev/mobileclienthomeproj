package com.example.stas.homeproj.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;
import com.example.stas.homeproj.view.InvoiceListItem;

/**
 * @author StasEvseev
 * Адаптер для накладных.
 */
public class InvoiceCursorBinderAdapter extends CursorAdapter {

    private final LayoutInflater mInflater;

    private final int mLayoutResId;

    public InvoiceCursorBinderAdapter(Context context, int layoutResId) {
        super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
        mInflater = LayoutInflater.from(context);
        mLayoutResId = layoutResId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(mLayoutResId, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//        InvoiceBuyApiHolder holder = new InvoiceBuyApiHolder();

        ((InvoiceListItem)view).bind(InvoiceBuyApiHolder.fromCursor(cursor));

    }

}