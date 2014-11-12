package com.example.stas.homeproj.library;

import android.content.Context;

import com.example.stas.homeproj.Constrants;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author StasEvseev
 * Класс помощник в работе с РЕСТ
 */
public class RestApiHelper {

    public static <T> T createResource(Class<T> cl, Context context) {
        AuthHelper auth = new AuthHelper(context);

        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
        requestInterceptor.setToken(new Token(auth.getToken()));
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
        RestAdapter restAdapter = restAdapterBuilder.setEndpoint(Constrants.URL_BUY_API).setConverter(new GsonConverter(gson)).build();
        return restAdapter.create(cl);
    }

}
