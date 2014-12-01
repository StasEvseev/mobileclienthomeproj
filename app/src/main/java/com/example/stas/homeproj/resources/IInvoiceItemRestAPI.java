package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.GoodsBuyApi;
import com.example.stas.homeproj.models.InvoiceItemBuyApi;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author StasEvseev
 * Ресурс для работы с товаром.
 */
public interface IInvoiceItemRestAPI {
    @GET("/api/invoice/{id}/goods")
    void goods(@Path("id") int invId, Callback<GoodsBuyApi> cb);


    @GET("/api/invoice/{id}/goods")
    GoodsBuyApi goodsSync(@Path("id") int invId);
//    @POST("/api/invoice/{id}/goods")
//    void save_bulk(@Path("id") int invId, List<>)

    @GET("/api/invoice/{id}/items")
    InvoiceItemBuyApi.InvoiceItemItemsBuyApi itemsSync(@Path("id") int invId);

}
