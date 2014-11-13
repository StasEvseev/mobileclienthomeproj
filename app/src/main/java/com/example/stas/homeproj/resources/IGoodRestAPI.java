package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.GoodsBuyApi;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author StasEvseev
 * Ресурс для работы с товаром.
 */
public interface IGoodRestAPI {
    @GET("/api/invoice/{id}/goods")
    void goods(@Path("id") int invId, Callback<GoodsBuyApi> cb);
}
