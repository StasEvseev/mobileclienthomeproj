package com.example.stas.homeproj.models;

/**
 * @author StasEvseev
 * Домейн для товаров
 */
public class GoodLocal {
    public GoodBuyApi good;
    public int id;
    //ID Good в локальной БД
    public int id_good_buy_api;
    public int factCount;
    public int barcode;

    public boolean is_sync;

    public GoodLocal(GoodBuyApi goodapi) {

        setGood(goodapi);
//        good = goodapi;
//        id = getId();
//        id_good_buy_api = goodapi.good_id;
        factCount = 0;
        barcode = 0;

        is_sync = true;
    }

    public GoodLocal() {
        is_sync = true;

    }

    public void setGood(GoodBuyApi buyApi) {
        good = buyApi;
        id_good_buy_api = buyApi.id;
    }

    public int getId() {
        return id;
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
