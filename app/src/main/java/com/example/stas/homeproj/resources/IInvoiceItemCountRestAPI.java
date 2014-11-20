package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.InvoiceCountResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by user on 20.11.14.
 */
public interface IInvoiceItemCountRestAPI {
    @GET("/api/invoice/{id}/count")
    InvoiceCountResult count(@Path("id") int invId);
}
