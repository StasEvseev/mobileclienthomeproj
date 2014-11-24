package com.example.stas.homeproj;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.stas.homeproj.data.InvoiceContent;
import com.example.stas.homeproj.db.dao.InvoiceBuyApiHolder;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.provider.InvoiceContentProvider;
import com.example.stas.homeproj.sync.AccountSyncHelper;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GoodDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link GoodListFragment} and the item details
 * (if present) is a {@link GoodDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link GoodListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class GoodListActivity extends Activity
        implements GoodListFragment.Callbacks, GoodDetailFragment.CallbacksSave, View.OnClickListener {

    public final static String KEY_INVOICE_ID = "invoice_id";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private int id_invoice;

    private Button btnSaveInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);

        btnSaveInvoice = (Button)findViewById(R.id.btn_save_invoice);
        btnSaveInvoice.setOnClickListener(this);

        id_invoice = getIntent().getIntExtra(KEY_INVOICE_ID, 0);
//        Cursor cur = ContentUris.withAppendedId(InvoiceContentProvider.CONTENT_URI, id_invoice);

        InvoiceBuyApi invoiceBuyApi = new InvoiceBuyApi();

        Cursor cur = getContentResolver().query(InvoiceContentProvider.CONTENT_URI, null,
                InvoiceBuyApiHolder.COL_ID + "=?", new String[] { String.valueOf(id_invoice) }, null);

        if(cur != null) {
            while (cur.moveToNext()) {
                invoiceBuyApi = InvoiceBuyApiHolder.fromCursor(cur);
            }
        }

//        InvoiceContentProvider

        setTitle("Накладная №" + invoiceBuyApi.toString());

        GoodListFragment glf = ((GoodListFragment) getFragmentManager()
                .findFragmentById(R.id.item_list));

        if (findViewById(R.id.good_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.

            glf.setActivateOnItemClick(true);

        }

        glf.loadGood(id_invoice);

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link GoodListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int id) {
        if (mTwoPane) {
            //В режиме двух панелей заменяем фрагмент
            Bundle arguments = new Bundle();
            arguments.putInt(GoodDetailFragment.ARG_ITEM_ID, id);
            GoodDetailFragment fragment = new GoodDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.good_detail_container, fragment)
                    .commit();

        } else {
            //В режиме одной панели открываем новое активити для детального просмотра товара с
            //переданным id в Intent
            Intent detailIntent = new Intent(this, GoodDetailActivity.class);
            detailIntent.putExtra(GoodDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onClick(View v) {
        Log.d("GoodListActivity", "Click");
//        Account mAccount = AccountSyncHelper.CreateSyncAccount(this);
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
//            mResolver = getContentResolver();
//            getContentResolver().requestSync(mAccount, AccountSyncHelper.AUTHORITY, settingsBundle);

        ContentResolver.requestSync(MyApplication.sAccount, MyApplication.AUTHORITY, settingsBundle);
    }
}
