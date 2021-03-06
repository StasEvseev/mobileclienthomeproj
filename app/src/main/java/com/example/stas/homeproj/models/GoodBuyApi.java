package com.example.stas.homeproj.models;

import java.io.Serializable;

/**
 * @author StasEvseev
 * Домен модель товара из BUY_API
 */
public class GoodBuyApi implements Serializable {
    public int count;
    public int count_order;
    public int count_postorder;
    public int count_whole_pack;
    public String full_name;
    public int id;
    public int invoice_id;
    public String name;
    public String number_global;
    public String number_local;
    public int placer;
    public Double price_with_NDS;
    public Double price_without_NDS;
    public Double rate_NDS;
    public Double sum_NDS;
    public Double sum_with_NDS;
    public Double sum_without_NDS;
    public String thematic;
    //ID Good into BUY_API
    public int good_id;

    @Override
    public String toString() {
        return full_name;
    }
}
