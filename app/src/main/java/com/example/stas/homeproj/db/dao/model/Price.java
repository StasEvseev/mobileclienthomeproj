package com.example.stas.homeproj.db.dao.model;

import java.util.Date;

/**
 * Created by user on 27.11.14.
 */
public class Price extends BaseSyncModel {
    public int commodity_id;
    public double NDS;
    public double price_prev;
    public double price_post;
    public String number_local;
    public String number_global;
    public double price_gross;
    public double price_retail;
    public Date date_from;
}
