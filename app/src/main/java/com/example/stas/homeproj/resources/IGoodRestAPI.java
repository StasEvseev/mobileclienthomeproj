package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.Goods;
import com.example.stas.homeproj.models.Invoices;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by user on 11.11.14.
 */
public interface IGoodRestAPI {
    @GET("/api/invoice/{id}/goods")
    void goods(@Path("id") String invId, Callback<Goods> cb);
}
