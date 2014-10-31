package com.example.stas.homeproj.resources;

import com.example.stas.homeproj.models.Book;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by stas on 31.10.14.
 */
public interface IBookSetRestAPI {
    @GET("/api/books/{book_id}")
    Book getBook(@Path("book_id") int book_id);
}
