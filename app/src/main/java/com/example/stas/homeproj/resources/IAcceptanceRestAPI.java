package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.AcceptanceBuyApi;

import retrofit.http.GET;

/**
 * Created by Stanislav on 01.12.2014.
 */
public interface IAcceptanceRestAPI {

    @GET("/api/acceptance")
    AcceptanceBuyApi.AcceptanceItemsBuyApi acceptanceSync();
}
