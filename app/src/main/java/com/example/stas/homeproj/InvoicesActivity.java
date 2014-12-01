package com.example.stas.homeproj;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.sync.SyncAdapter;
import com.example.stas.homeproj.widget.InvoiceCursorBinderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author StasEvseev
 * Активити выбора накладной
 **/

public class InvoicesActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>,
        SyncStatusObserver, AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public final String LOG = InvoicesActivity.class.getName();

    public ListView lv;
    public List<Invoice> lstr;
    private Button btnSync;

    private Object mSyncMonitor;

    private CursorAdapter mListAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private BroadcastReceiver syncFinishedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mSwipeRefreshLayout.setRefreshing(false);
//            Log.d(Const.TAG, "Sync finished, should refresh nao!!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // делаем повеселее
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_dark,
                android.R.color.primary_text_dark, android.R.color.holo_red_dark);

//        btnSync = (Button)findViewById(R.id.btn_sync);
//        btnSync.setOnClickListener(this);

        lstr = new ArrayList<Invoice>();

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

        registerReceiver(syncFinishedReceiver, new IntentFilter(SyncAdapter.SYNC_FINISHED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContentResolver.removeStatusChangeListener(mSyncMonitor);
        unregisterReceiver(syncFinishedReceiver);
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

        final Invoice inv = (Invoice) parent.getItemAtPosition(position);

        if (inv.is_handle(this)) {
            AlertDialog.Builder ad;
            final Context context = this;

            ad = new AlertDialog.Builder(this);
            ad.setTitle("Внимание");  // заголовок
            ad.setMessage("У выбранной накладной уже есть приемка."); // сообщение
            ad.setPositiveButton("Открыть прошлую приемку", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    nextStep(inv, false);
                }
            });
            ad.setNegativeButton("Создать новую приемку", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    nextStep(inv, true);
//                    Toast.makeText(context, "Возможно вы правы", Toast.LENGTH_LONG)
//                            .show();
                }
            });
            ad.setCancelable(true);
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(context, "Вы ничего не выбрали",
                            Toast.LENGTH_LONG).show();
                }
            });
            ad.show();
        } else {
            nextStep(inv, true);
        }

//        final InvoiceBuyApi inv = (InvoiceBuyApi) parent.getItemAtPosition(position);
//        Intent intent = new Intent(InvoicesActivity.this, GoodListActivity.class);
//        Log.d(LOG, "INVOIC_ID - " + inv.id);
//        intent.putExtra(GoodListActivity.KEY_INVOICE_ID, inv.id);
//        startActivity(intent);
    }

    public void nextStep(Invoice inv, boolean create_new) {

        if(create_new) {
//            Acceptance acceptance = inv.createAcceptance(this);
        }

        Intent intent = new Intent(InvoicesActivity.this, GoodListActivity.class);
        Log.d(LOG, "INVOIC_ID - " + inv.id_buy_api);
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
        ContentResolver.requestSync(Session.mAccount, MyApplication.AUTHORITY, settingsBundle);
    }

    @Override
    public void onStatusChanged(int which) {
        Log.d(LOG, "onStatusChanged - " + which);

//        mSwipeRefreshLayout.setOnRefreshListener();

//        if (which == ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE) {
//
//        }



        lv.deferNotifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG, "onCreateLoader");
        return new CursorLoader(
                getApplicationContext(),
                MainContentProvider.CONTENT_URI_INVOICE, null, null, null, null
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

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(Session.mAccount, MyApplication.AUTHORITY, settingsBundle);
        // ждем 3 секунды и прячем прогресс

//        mSwipeRefreshLayout.post
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {

                // говорим о том, что собираемся закончить
//                Toast.makeText(InvoicesActivity.this, R.string.refresh_finished, Toast.LENGTH_SHORT).show();
//            }
//        }, 3000);
    }
}
