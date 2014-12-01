package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.ProviderBuyApi;

import retrofit.http.GET;

/**
 * Created by Stanislav on 01.12.2014.
 */
public interface IProviderRestApi {
    @GET("/api/provider")
    ProviderBuyApi.ProviderItemsBuyApi providerSync();
}
