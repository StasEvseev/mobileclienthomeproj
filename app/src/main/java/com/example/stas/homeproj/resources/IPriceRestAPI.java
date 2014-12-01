package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.PriceBuyApi;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Stanislav on 01.12.2014.
 */
public interface IPriceRestAPI {

    @GET("/api/price")
    PriceBuyApi.PriceItemsBuyApi priceSync();
}
