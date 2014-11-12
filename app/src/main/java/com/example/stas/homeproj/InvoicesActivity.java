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

import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.Invoice;
import com.example.stas.homeproj.models.Invoices;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InvoicesActivity extends Activity {

    public ArrayAdapter<Invoice> adapter;
    public ListView lv;
    public List<Invoice> lstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);

        lstr = new ArrayList<Invoice>();

        lv = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<Invoice>(
                this , android.R.layout.simple_list_item_1);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Invoice inv = (Invoice) parent.getItemAtPosition(position);
                Log.d("DEBUG!!!", String.valueOf(position));
                Log.d("DEBUG!!!", String.valueOf(inv.id));

                Intent intent = new Intent(InvoicesActivity.this, GoodListActivity.class);
                intent.putExtra("ID", inv.id);

                startActivity(intent);
            }
        });

        IInvoiceRestAPI invoice_api = RestApiHelper.createResource(IInvoiceRestAPI.class, this);
        invoice_api.invoices(new Callback<Invoices>() {
            @Override
            public void success(Invoices invs, Response response) {

                for(int i = 0; i < invs.items.size(); i++) {
                    lstr.add(invs.items.get(i));
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
