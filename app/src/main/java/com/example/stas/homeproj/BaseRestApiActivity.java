package com.example.stas.homeproj;

import android.app.Activity;

import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.example.stas.homeproj.resources.IGoodRestAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 *
 */
public class BaseRestApiActivity extends Activity {
//    public <T> T createResource(Class<T> cl) {
//        AuthHelper auth = new AuthHelper(this);
//
//        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
//        requestInterceptor.setToken(new Token(auth.getToken()));
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd")
//                .create();
//        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
//        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
//        RestAdapter restAdapter = restAdapterBuilder.setEndpoint(Constrants.URL_BUY_API).setConverter(new GsonConverter(gson)).build();
//        return restAdapter.create(cl);
//    }
}
