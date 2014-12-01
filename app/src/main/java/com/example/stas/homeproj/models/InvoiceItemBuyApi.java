package com.example.stas.homeproj.models;

import java.util.List;

/**
 * Created by Stanislav on 01.12.2014.
 */
public class InvoiceItemBuyApi {
    public int commodity_id;
    public int count;
    public int count_order;
    public int count_postorder;
    public int count_whole_pack;
    public String full_name;
    public int good_id;
    public int id;
    public String name;
    public String number_local;
    public String number_global;
    public int placer;
    public int price_id;
    public Double price_with_NDS;
    public Double price_without_NDS;
    public Float rate_NDS;
    public Double sum_NDS;
    public Double sum_with_NDS;
    public Double sum_without_NDS;
    public String thematic;
    public int barcode;
    public String factcount;

    public class InvoiceItemItemsBuyApi {
        public List<InvoiceItemBuyApi> items;
    }
}
