package com.example.stas.homeproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.models.Invoice;
import com.example.stas.homeproj.models.Invoices;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.models.User;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.example.stas.homeproj.resources.IAuthSetRestAPI;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class InvoicesActivity extends Activity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    public ArrayAdapter<String> adapter;
    public ListView lv;
    public List<String> lstr;
    public HashMap<Integer, Invoice> hInv = new HashMap<Integer, Invoice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);

        lstr = new ArrayList<String>();

        lv = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(
                this , android.R.layout.simple_list_item_1);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Log.d("DEBUG!!!", String.valueOf(position));
                Log.d("DEBUG!!!", item);

                Intent intent = new Intent(InvoicesActivity.this, ItemListActivity.class);
                intent.putExtra("ID", item);

                startActivity(intent);
//                adapter.remove(item);
            }
        });

        AuthHelper auth = new AuthHelper(this);

        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
        requestInterceptor.setToken(new Token(auth.getToken()));
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
        RestAdapter restAdapter = restAdapterBuilder.setEndpoint(Constrants.URL_BUY_API).setConverter(new GsonConverter(gson)).build();
        IInvoiceRestAPI invoice_api = restAdapter.create(IInvoiceRestAPI.class);

        invoice_api.invoices(new Callback<Invoices>() {
            @Override
            public void success(Invoices invs, Response response) {

                for(int i = 0; i < invs.items.size(); i++) {
                    lstr.add(String.valueOf(invs.items.get(i).id));
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
        getMenuInflater().inflate(R.menu.invoices, menu);
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
