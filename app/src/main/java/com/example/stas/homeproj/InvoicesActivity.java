package com.example.stas.homeproj;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.provider.InvoiceContentProvider;
import com.example.stas.homeproj.sync.AccountSyncHelper;
import com.example.stas.homeproj.widget.InvoiceCursorBinderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author StasEvseev
 * Активити выбора накладной
 **/

public class InvoicesActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>,
        SyncStatusObserver, AdapterView.OnItemClickListener, View.OnClickListener {

    public final String LOG = InvoicesActivity.class.getName();

    public ListView lv;
    public List<InvoiceBuyApi> lstr;
    private Button btnSync;

    private Object mSyncMonitor;

    private CursorAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);

        btnSync = (Button)findViewById(R.id.btn_sync);
        btnSync.setOnClickListener(this);

        lstr = new ArrayList<InvoiceBuyApi>();

        lv = (ListView)findViewById(R.id.listView);

        mListAdapter = new InvoiceCursorBinderAdapter(this, R.layout.li_invoice);

        lv.setAdapter(mListAdapter);

        lv.setOnItemClickListener(this);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSyncMonitor = ContentResolver.addStatusChangeListener(
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE
                        | ContentResolver.SYNC_OBSERVER_TYPE_PENDING,
                this
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContentResolver.removeStatusChangeListener(mSyncMonitor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG, "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.invoices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG, "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * При выборе накладной -> активити товара
    * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG, "onItemClick");



        final InvoiceBuyApi inv = (InvoiceBuyApi) parent.getItemAtPosition(position);
        Intent intent = new Intent(InvoicesActivity.this, GoodListActivity.class);
        Log.d(LOG, "INVOIC_ID - " + inv.id);
        intent.putExtra(GoodListActivity.KEY_INVOICE_ID, inv.id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        ContentResolver.requestSync(AccountSyncHelper.CreateSyncAccount(this), AccountSyncHelper.AUTHORITY, settingsBundle);
    }

    @Override
    public void onStatusChanged(int which) {
        Log.d(LOG, "onStatusChanged - " + which);

        lv.deferNotifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG, "onCreateLoader");
        return new CursorLoader(
                getApplicationContext(),
                InvoiceContentProvider.CONTENT_URI, null, null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG, "onLoadFinished");
        mListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG, "onLoadReset");
        mListAdapter.swapCursor(null);
    }
}
