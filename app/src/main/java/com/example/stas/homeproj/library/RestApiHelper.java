package com.example.stas.homeproj.library;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.util.Log;

import com.example.stas.homeproj.Constrants;
import com.example.stas.homeproj.Session;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * @author StasEvseev
 * Класс помощник в работе с РЕСТ
 */
public class RestApiHelper {

    public static <T> T createResource(Class<T> cl, Context context, String token) {
        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
        requestInterceptor.setToken(new Token(token));
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
        RestAdapter restAdapter = restAdapterBuilder
                .setEndpoint(Constrants.URL_BUY_API)
                .setConverter(new GsonConverter(gson))
                .setErrorHandler(new MyErrorHandler())
                .build();
        return restAdapter.create(cl);
    }
}

class UnauthorizedException extends Exception {

    UnauthorizedException(RetrofitError err) {
        Log.d("EXCPTION", "EXCEPTION");
//        super(message, url, response, converter, successType, kind, exception);
    }
}

class MyErrorHandler implements ErrorHandler {
    @Override public Throwable handleError(RetrofitError cause) {
        Log.d("MYERROR", "HANDLE");
        Response r = cause.getResponse();

        Log.d("ERROR", "");
//        Log.d("ERROR", r.getBody().toString());
//        Log.d("ERROR", r.getHeaders().toString());
//        Log.d("ERROR", String.valueOf(r.getStatus()));

        if (r != null && r.getStatus() == 401) {
            return new UnauthorizedException(cause);
        }
        return cause;
    }
}