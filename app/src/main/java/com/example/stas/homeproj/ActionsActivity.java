package com.example.stas.homeproj;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        btnGood = (Button)findViewById(R.id.goods);

        btnGood.setOnClickListener(this);

        mAccount = AccountSyncHelper.CreateSyncAccount(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    }
}
