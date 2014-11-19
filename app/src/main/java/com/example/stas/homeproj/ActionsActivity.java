package com.example.stas.homeproj;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.sync.AccountSyncHelper;

/**
 * @author StasEvseev
 * Активити меню действий пользователя(Прием почты и прочее).
 **/

public class ActionsActivity extends Activity implements View.OnClickListener {

    Button btnGood;

    // Constants
    // Content provider authority
//    public static final String AUTHORITY = "com.example.android.datasync.provider";
    // Account
//    public static final String ACCOUNT = "default_account";
    // Sync interval constants
    public static final long MILLISECONDS_PER_SECOND = 1000L;
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
    public static final long SYNC_INTERVAL =
            1000L * 3;

    // Instance fields
    Account mAccount;
//    ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        btnGood = (Button)findViewById(R.id.goods);
//        btnSync = (Button)findViewById(R.id.btn_sync);

        btnGood.setOnClickListener(this);
//        btnSync.setOnClickListener(this);

        mAccount = AccountSyncHelper.CreateSyncAccount(this);

        // Get the content resolver for your app
//        mResolver = getContentResolver();
        /*
         * Turn on periodic syncing
         */

        Bundle settingsBundle = new Bundle();
//        settingsBundle.putBoolean(
//                ContentResolver.SYNC_EXTRAS_MANUAL, true);
//        settingsBundle.putBoolean(
//                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        ContentResolver.setIsSyncable(mAccount, AccountSyncHelper.AUTHORITY, 1);
//        ContentResolver.addPeriodicSync(
//                mAccount,
//                AccountSyncHelper.AUTHORITY,
//                settingsBundle,
//                SYNC_INTERVAL);
//
//
//        getContentResolver().setIsSyncable(mAccount, AccountSyncHelper.AUTHORITY, 0);
//        getContentResolver().addPeriodicSync(
//                mAccount,
//                AccountSyncHelper.AUTHORITY,
//                settingsBundle,
//                SYNC_INTERVAL);
//        ContentResolver.setSyncAutomatically(mAccount, AccountSyncHelper.AUTHORITY, false);
//        getContentResolver().setSyncAutomatically(mAccount, AccountSyncHelper.AUTHORITY, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Логаут из системы
    * */
    public void logout() {
        AuthHelper auth = new AuthHelper(getApplicationContext());
        auth.setToken("");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == btnGood) {
            /*
            * Принимаем присланный товар
            * */
            startActivity(new Intent(this, InvoicesActivity.class));
        }
//        else if (view == btnSync) {
//            Log.d("BtnSync", "Click");
//            // Pass the settings flags by inserting them in a bundle
//            Bundle settingsBundle = new Bundle();
//            settingsBundle.putBoolean(
//                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
//            settingsBundle.putBoolean(
//                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        /*
//         * Request the sync for the default account, authority, and
//         * manual sync settings
//         */
////            mResolver = getContentResolver();
////            getContentResolver().requestSync(mAccount, AccountSyncHelper.AUTHORITY, settingsBundle);
//
//            ContentResolver.requestSync(mAccount, AccountSyncHelper.AUTHORITY, settingsBundle);
//        }
    }
}
