package com.example.stas.homeproj.library;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.stas.homeproj.Constrants;

/**
 * @author StasEvseev
 * Класс помощник работы с авторизацией в системе.
 */
public final class AuthHelper {

    public final String KEY = "homeproj.session";

    private Context context;

    public AuthHelper(Context context) {
        this.context = context;
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(Constrants.TOKEN_NAME, token).apply();
    }

    public String getToken() {
        SharedPreferences spref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return spref.getString(Constrants.TOKEN_NAME, "");
    }

    public boolean checkAuth() {
        String token = getToken();
        return !"".equals(token);
    }

}
