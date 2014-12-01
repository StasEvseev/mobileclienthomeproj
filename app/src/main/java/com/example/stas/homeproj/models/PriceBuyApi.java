package com.example.stas.homeproj.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Stanislav on 01.12.2014.
 */
public class PriceBuyApi {
    public int id;
    public int commodity_id;
    public Double NDS;
    public Double price_prev;
    public Double price_post;
    public String number_local;
    public String number_global;
    public Double price_gross;
    public Double price_retail;
    public Date date_from;

    public class PriceItemsBuyApi {
        public List<PriceBuyApi> items;
    }
}
