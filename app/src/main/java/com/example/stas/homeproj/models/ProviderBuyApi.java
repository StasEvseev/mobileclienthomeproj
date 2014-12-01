package com.example.stas.homeproj.models;

import java.util.List;

/**
 * Created by Stanislav on 01.12.2014.
 */
public class ProviderBuyApi {
    public int id;
    public String name;
    public String address;
    public String emails;

    public class ProviderItemsBuyApi {
        public List<ProviderBuyApi> items;
    }
}
