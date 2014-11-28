package com.example.stas.homeproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * @author StasEvseev
 * Активити запрашивающее авторизацию, и при наличии ее перенаправляет на ActionsActivity.
 * */

public class MainActivity extends Activity {

    static final int REQUEST_LOGIN = 99;
    // Instance fields
//    Account mAccount;
//    AccountManager mAccountManager;

    public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String ACCOUNT_PASSWORD = "ACCOUNT_PASSWORD";
    public static final String ACCOUNT_TOKEN = "ACCOUNT_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Если пользователь на авторизован, вежливо попросим сделать это
        if (Session.checkAuth(this)) {
            Session.signIn(this);
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
                    String accountName = data.getStringExtra(ACCOUNT_NAME);
                    String pass = data.getStringExtra(ACCOUNT_PASSWORD);
                    String token = data.getStringExtra(ACCOUNT_TOKEN);

                    try {
                        Session.registration(accountName, pass, token, this);
                        Session.signIn(this);
                    } catch (SingleRegistration singleRegistration) {
                        singleRegistration.printStackTrace();
                    }

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
