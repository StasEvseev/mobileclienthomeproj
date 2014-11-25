package com.example.stas.homeproj;

//import android.accounts.AbstractAccountAuthenticator;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 25.11.14.
 */
//public class AuthenticatorActivity extends AccountAuthenticatorActivity {
//    private AccountManager mAccountManager;
//    private String mAuthTokenType;
//
//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_login);
//        mAccountManager = AccountManager.get(getBaseContext());
//
//        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
//        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
//
//        if (mAuthTokenType == null)
//            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
//
//        if (accountName != null) {
//            ((TextView) findViewById(R.id.accountName)).setText(accountName);
//        }
//
//        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//            }
//        });
//        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
//                // and return them in setAccountAuthenticatorResult(). See finishLogin().
//                Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
//                signup.putExtras(getIntent().getExtras());
//                startActivityForResult(signup, REQ_SIGNUP);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        // The sign up activity returned that the user has successfully created an account
//        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
//            finishLogin(data);
//        } else
//            super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public void submit() {
//
//        final String userName = ((TextView) findViewById(R.id.accountName)).getText().toString();
//        final String userPass = ((TextView) findViewById(R.id.accountPassword)).getText().toString();
//
//        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
//
//        new AsyncTask<String, Void, Intent>() {
//
//            @Override
//            protected Intent doInBackground(String... params) {
//
//                Log.d("udinic", TAG + "> Started authenticating");
//
//                Bundle data = new Bundle();
//                try {
//                    User user = sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);
//
//                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
//                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//                    data.putString(AccountManager.KEY_AUTHTOKEN, user.getSessionToken());
//
//                    // We keep the user's object id as an extra data on the account.
//                    // It's used later for determine ACL for the data we send to the Parse.com service
//                    Bundle userData = new Bundle();
//                    userData.putString(USERDATA_USER_OBJ_ID, user.getObjectId());
//                    data.putBundle(AccountManager.KEY_USERDATA, userData);
//
//                    data.putString(PARAM_USER_PASS, userPass);
//
//                } catch (Exception e) {
//                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
//                }
//
//                final Intent res = new Intent();
//                res.putExtras(data);
//                return res;
//            }
//
//            @Override
//            protected void onPostExecute(Intent intent) {
//                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
//                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
//                } else {
//                    finishLogin(intent);
//                }
//            }
//        }.execute();
//    }
//
//    private void finishLogin(Intent intent) {
//        Log.d("udinic", TAG + "> finishLogin");
//
//        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
//        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
//
//        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
//            Log.d("udinic", TAG + "> finishLogin > addAccountExplicitly");
//            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
//            String authtokenType = mAuthTokenType;
//
//            // Creating the account on the device and setting the auth token we got
//            // (Not setting the auth token will cause another call to the server to authenticate the user)
//            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
//            mAccountManager.setAuthToken(account, authtokenType, authtoken);
//        } else {
//            Log.d("udinic", TAG + "> finishLogin > setPassword");
//            mAccountManager.setPassword(account, accountPassword);
//        }
//
//        setAccountAuthenticatorResult(intent.getExtras());
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//}