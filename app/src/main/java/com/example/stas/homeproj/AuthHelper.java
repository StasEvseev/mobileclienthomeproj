package com.example.stas.homeproj;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 07.11.14.
 */
public final class AuthHelper {

    public final String KEY = "homeproj.session";

    private Context context;

    public AuthHelper(Context context) {
        this.context = context;
    }

//    AuthHelper getSession(Context context) {
//        return new AuthHelper();
//    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(Constrants.TOKEN_NAME, token);
    }

    public String getToken() {
        SharedPreferences spref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return spref.getString(Constrants.TOKEN_NAME, "");
    }

    public boolean checkAuth() {
//        Context context = getApplicationContext();
        String token = getToken();
        return !"".equals(token);
    }

}
