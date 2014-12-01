package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoicesBuyApi;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * @author StasEvseev
 * Ресурс для работы с накладными.
 */
public interface IInvoiceRestAPI {
    @GET("/api/invoice")
    InvoicesBuyApi invoices(@QueryMap Map<String, String> options);

    @GET("/api/invoice")
    InvoiceBuyApi.InvoiceItemsBuyApi invoiceSync();
}
