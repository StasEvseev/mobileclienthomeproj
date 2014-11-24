package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.Good;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.models.GoodsBuyApi;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by user on 17.11.14.
 */
public interface IGoodRestAPI {
    @POST("/api/good/{id}")
    String save(@Path("id") int id, @Body GoodLocal good);
}