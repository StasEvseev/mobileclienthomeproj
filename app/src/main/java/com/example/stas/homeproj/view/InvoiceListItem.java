package com.example.stas.homeproj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stas.homeproj.R;
import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.models.InvoiceBuyApi;


/**
 * @author StasEvseev
 * Отображение одной накладной.
 */


public class InvoiceListItem extends LinearLayout {

    private TextView mTitle;

    public InvoiceListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Invoice invoice) {
        mTitle.setText(invoice.toString());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.title);
    }

}