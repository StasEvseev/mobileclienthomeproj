package com.example.stas.homeproj.models;

/**
 * @author StasEvseev
 * Домейн для товаров
 */
public class GoodLocal {
    public GoodBuyApi good;
    public int factCount;
    public int barcode;

    public GoodLocal(GoodBuyApi goodapi) {
        good = goodapi;
        factCount = 0;
        barcode = 0;
    }

    public int getId() {
        return good.id;
    }

    @Override
    public String toString() {
        return good.toString();
    }

    public String getFullname() {
        return good.full_name;
    }

    public int getCount() {
        return good.count;
    }
}
