package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.Token;

import retrofit.http.GET;

/**
 * @author StasEvseev
 * Ресурс для получения токена в BUY_API
 */
public interface IAuthSetRestAPI {
    @GET("/api/token")
    Token auth();
}