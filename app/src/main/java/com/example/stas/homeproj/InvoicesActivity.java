package com.example.stas.homeproj;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stas.homeproj.library.AuthHelper;
import com.example.stas.homeproj.models.Invoice;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.models.User;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.example.stas.homeproj.resources.IAuthSetRestAPI;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InvoicesActivity extends Activity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);

        ListView lv = (ListView)findViewById(R.id.listView);

        AuthHelper auth = new AuthHelper(this);

        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
//        requestInterceptor.setToken(new Token(auth.getToken())); // I pass the user from my model
        User user = new User();
        user.username = "Stas";
        user.password = "123";
        requestInterceptor.setUser(user);

        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
        RestAdapter restAdapter = restAdapterBuilder.setEndpoint(Constrants.URL_BUY_API).build();
        IInvoiceRestAPI invoice_api = restAdapter.create(IInvoiceRestAPI.class);

        invoice_api.invoices(new Callback() {
            @Override
            public void success(Object o, Response response) {
                Log.d("DEBUGGG!!!", "SUCCESS");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("DEBUGGG!!!", "FAILURE");
            }
        });

//        #for(int i = 0; i < linvoice.size(); i++) {
//            Log.d("DEBUGGG!!!", linvoice.get(i).number.toString());
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, names);

        lv.setAdapter(adapter);
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
