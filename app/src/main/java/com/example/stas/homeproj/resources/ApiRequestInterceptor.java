package com.example.stas.homeproj.resources;

import android.util.Base64;

import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.models.User;

import retrofit.RequestInterceptor;

/**
 * @author StasEvseev
 * Интерцептор для авторизации по логину и паролю, либо по токену.
 */
public class ApiRequestInterceptor implements RequestInterceptor {

    private User user;
    private Token token;

    @Override
    public void intercept(RequestFacade requestFacade) {

        if (user != null || token != null) {
            final String authorizationValue = encodeCredentialsForBasicAuthorization();
            requestFacade.addHeader("Authorization", authorizationValue);
        }
    }

    private String encodeCredentialsForBasicAuthorization() {
        String userAndPassword = "";
        if (user != null) {
            userAndPassword = user.getUsername() + ":" + user.getPassword();
        } else if (token != null) {
            userAndPassword = token.token + ":unused";
        }

        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.token = null;
    }

    public void setToken(Token token) {
        this.token = token;
        this.user = null;
    }
}
