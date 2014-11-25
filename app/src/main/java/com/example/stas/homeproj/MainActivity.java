package com.example.stas.homeproj;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.sync.AccountSyncHelper;

/**
 * @author StasEvseev
 * Активити запрашивающее авторизацию, и при наличии ее перенаправляет на ActionsActivity.
 * */

public class MainActivity extends Activity {

    static final int REQUEST_LOGIN = 99;
    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthHelper auth = new AuthHelper(getApplicationContext());


//        ContentResolver.addPeriodicSync(
//                MyApplication.sAccount, MyApplication.AUTHORITY,
//                new Bundle(Bundle.EMPTY),
//                60 * 2);

        //Если пользователь на авторизован, вежливо попросим сделать это
        if (auth.checkAuth()) {
//            mAccount = AccountSyncHelper.CreateSyncAccount(this);
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
