package com.example.stas.homeproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    static final int REQUEST_LOGIN = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        SharedPreferences spref = context.getSharedPreferences("homeproj.session", Context.MODE_PRIVATE);

        String token = spref.getString("BUY_API_TOKEN", "");

        if ("".equals(token)) {
            startActivityForResult(new Intent(this, LoginActivity2.class), REQUEST_LOGIN);
        } else {
            startActivity(new Intent(this, ActionsActivity.class));
            finish();
        }
//        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(this, ActionsActivity.class));
                } else {
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
