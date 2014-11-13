package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.InvoicesBuyApi;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author StasEvseev
 * Ресурс для работы с накладными.
 */
public interface IInvoiceRestAPI {
    @GET("/api/invoice")
    void invoices(Callback<InvoicesBuyApi> cb);
}
