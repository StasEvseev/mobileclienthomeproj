package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.CommodityBuyApi;

import retrofit.http.GET;

/**
 * Created by Stanislav on 01.12.2014.
 */
public interface ICommodityRestAPI {

    @GET("/api/commodity")
    CommodityBuyApi.CommodityItemsBuyApi commoditySync();
}
