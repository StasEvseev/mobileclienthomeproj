package com.example.stas.homeproj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
    private TextView mDate;
    private TextView mAcceptance;

    public InvoiceListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Invoice invoice) {
        mTitle.setText(invoice.toString());
//        mDate.setText(invoice.getDateToString());
//        mAcceptance.setText(invoice.getProviderString(getContext()));
    }

    @Override
    protected void onFinishInflate() {
        Log.d("InvoiceLListItem", "INFLATE");
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.number);
        mDate = (TextView) findViewById(R.id.date_invoice);
        mAcceptance = (TextView) findViewById(R.id.date_acceptance);
    }

}