package com.example.stas.homeproj.db.dao.model;

/**
 * Created by user on 27.11.14.
 */
public class InvoiceItem extends BaseSyncModel {
    public int commodity_id;
    public int price_id;
    public String fullname;
    public String number_local;
    public String number_global;
    public int factcount;
    public int barcode;
}
