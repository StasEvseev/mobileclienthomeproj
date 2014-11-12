package com.example.stas.homeproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.models.Goods;
import com.example.stas.homeproj.models.Invoices;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.example.stas.homeproj.resources.IGoodRestAPI;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class GoogsActivity extends Activity {

    public ArrayAdapter<String> adapter;
    public ListView lv;
    public List<String> lstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googs);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        lv = (ListView)findViewById(R.id.listGoods);
        adapter = new ArrayAdapter<String>(
                this , android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        lstr = new ArrayList<String>();

        AuthHelper auth = new AuthHelper(this);

        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
        requestInterceptor.setToken(new Token(auth.getToken()));
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
        RestAdapter restAdapter = restAdapterBuilder.setEndpoint(Constrants.URL_BUY_API).setConverter(new GsonConverter(gson)).build();
        IGoodRestAPI good_api = restAdapter.create(IGoodRestAPI.class);

        good_api.goods(Integer.parseInt(id), new Callback<Goods>() {
            @Override
            public void success(Goods goods, Response response) {
                Log.d("DEBUGGG!!!", "SUCCESS");
                for (int i = 0; i < goods.items.size(); i++) {
                    lstr.add(goods.items.get(i).full_name);
                }
                adapter.addAll(lstr);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("DEBUGGG!!!", retrofitError.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.googs, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
