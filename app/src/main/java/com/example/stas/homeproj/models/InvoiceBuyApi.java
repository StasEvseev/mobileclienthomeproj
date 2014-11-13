package com.example.stas.homeproj.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author StasEvseev
 * Домен модель накладной из BUY_API
 */
public class InvoiceBuyApi {
    public int id;
    public String number;
    public Date date;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s от %s", this.number, sdf.format(this.date));
    }
}
