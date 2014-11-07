package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.Book;
import com.example.stas.homeproj.models.Token;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by stas on 31.10.14.
 */
public interface IAuthSetRestAPI {
    @GET("/api/token")
    Token auth();
}
