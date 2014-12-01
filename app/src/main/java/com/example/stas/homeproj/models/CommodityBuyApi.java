package com.example.stas.homeproj.models;

import java.util.List;

/**
 * Created by Stanislav on 01.12.2014.
 */
public class CommodityBuyApi {
    public int id;
    public String name;
    public String category;

    public class CommodityItemsBuyApi {
        public List<CommodityBuyApi> items;
    }
}
