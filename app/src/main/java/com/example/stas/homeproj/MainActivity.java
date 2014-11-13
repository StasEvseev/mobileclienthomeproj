package com.example.stas.homeproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.stas.homeproj.library.AuthHelper;

/**
 * @author StasEvseev
 * Активити запрашивающее авторизацию, и при наличии ее перенаправляет на ActionsActivity.
 * */

public class MainActivity extends Activity {

    static final int REQUEST_LOGIN = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthHelper auth = new AuthHelper(getApplicationContext());

        //Если пользователь на авторизован, вежливо попросим сделать это
        if (auth.checkAuth()) {
            startActivity(new Intent(this, ActionsActivity.class));
            finish();
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(this, ActionsActivity.class));
                    finish();
                } else {
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
