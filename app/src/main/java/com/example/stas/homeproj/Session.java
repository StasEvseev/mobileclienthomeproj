package com.example.stas.homeproj;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by user on 25.11.14.
 */

class SingleRegistration extends Exception {
    public SingleRegistration(String only) {
        super(only);
    }
}

public class Session {

//    public final static String ACCOUNT_TYPE = "";
    public static Account mAccount;
    public static final String TOKEN_TYPE = "token.type";
//    public static AccountManager mAccountManager;

    public static void registration(String accName, String pass, String token, Context context) throws SingleRegistration {
        Account acc = new Account(accName, MyApplication.ACCOUNT_TYPE);

        AccountManager mAccountManager = AccountManager.get(context);

        if (checkAuth(context)) {
            throw new SingleRegistration("Only single");
        }

        mAccountManager.addAccountExplicitly(acc, pass, null);
        mAccountManager.setAuthToken(acc, TOKEN_TYPE, token);
    }

    public static Account getAccount(Context context) {
        if (checkAuth(context)) {
            Account [] acc = AccountManager.get(context).getAccountsByType(MyApplication.ACCOUNT_TYPE);
            return acc[0];
        }
        return null;
    }

    public static boolean signIn(Context context) {
        Account acc = getAccount(context);
        if (acc == null) {
            return false;
        } else {
            mAccount = acc;
            return true;
        }
    }

    public static boolean signOut(Context context) {
        Account acc = getAccount(context);
        if (acc == null) {
//            return true;
        } else {
            mAccount = null;
            AccountManager.get(context).removeAccount(acc, null, null);
        }

//        AccountManager.get(context).removeAccount()
        return true;
    }

    public static boolean checkAuth(Context context){
        Account [] acc = AccountManager.get(context).getAccountsByType(MyApplication.ACCOUNT_TYPE);
        return acc.length > 0;
    }



}
