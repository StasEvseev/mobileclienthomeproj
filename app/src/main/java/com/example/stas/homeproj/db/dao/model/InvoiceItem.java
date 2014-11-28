package com.example.stas.homeproj.db.dao.model;

/**
 * Created by user on 27.11.14.
 */
public class InvoiceItem extends BaseSyncModel {
    public int invoice_id;
    public int commodity_id;
    public int price_id;
    public String fullname;
    public String number_local;
    public String number_global;
    public int count;
//    Россыпь
    public int placer;
//    Целых пачек
    public int count_whole_pack;
    public int factcount;
    public int barcode;
}
