package com.example.stas.homeproj;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.os.Bundle;

/**
 * Created by user on 24.11.14.
 */
public class MyApplication extends Application {

    public static final String ACCOUNT_TYPE = "com.example.stas.homeproj";

    public static final String AUTHORITY = "com.example.stas.homeproj";
    // The account name
    public static final String ACCOUNT = "syncAccount";

    public static Account sAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        final AccountManager am = AccountManager.get(this);

        if (sAccount == null) {
            sAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        }
        if (am.addAccountExplicitly(sAccount, null, null)) {
            ContentResolver.setSyncAutomatically(sAccount, AUTHORITY, true);
        }
    }

}
