package com.example.stas.homeproj;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.stas.homeproj.db.dao.Helper;
import com.example.stas.homeproj.db.dao.InvoiceHolder;
import com.example.stas.homeproj.db.dao.model.Acceptance;
import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.library.AlertHelper;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.provider.helper.ProviderContent;

import java.text.ParseException;

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
        implements GoodListFragment.Callbacks, GoodDetailFragment.CallbacksSave, View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    public final static String KEY_INVOICE_ID = "invoice_id";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private int id_invoice;
    private Invoice invoice;
    private Acceptance acceptance;

    private Button btnSaveInvoice;

    private EditText dateInvoice;
    private EditText dateAcceptance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);

        btnSaveInvoice = (Button)findViewById(R.id.btn_save_invoice);
        btnSaveInvoice.setOnClickListener(this);

        id_invoice = getIntent().getIntExtra(KEY_INVOICE_ID, 0);

        invoice = (Invoice) ProviderContent.getById(this, MainContentProvider.CONTENT_URI_INVOICE,
                InvoiceHolder.COL_ID, id_invoice, Invoice.class, InvoiceHolder.class);

        setTitle("Накладная №" + invoice.toString());

        GoodListFragment glf = ((GoodListFragment) getFragmentManager()
                .findFragmentById(R.id.item_list));

        dateInvoice = (EditText)findViewById(R.id.date_invoice);
        dateAcceptance = (EditText)findViewById(R.id.date_acceptance);

        dateInvoice.setKeyListener(null);
        dateAcceptance.setKeyListener(null);

        dateInvoice.setText(invoice.getDateToString());

        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);

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

        if(v.getId() == R.id.btn_save_invoice) {
            Log.d("GoodListActivity", "Click");
            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

            ContentResolver.requestSync(Session.mAccount, MyApplication.AUTHORITY, settingsBundle);
        } else if (v.getId() == R.id.imageButton) {
            showDatePicker();
//            showDialog(1);
        }

    }

    public void showDatePicker() {

        AlertHelper.showDatePicker(this, this);

//        Calendar calender = Calendar.getInstance();
//
//        DatePickerDialog dialog = new DatePickerDialog(this, this, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
//
//        if (Build.VERSION.SDK_INT >= 11) {
//            dialog.getDatePicker().setCalendarViewShown(false);
//        }
//
//        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = new StringBuilder().append(year).append("-").append(monthOfYear + 1)
                .append("-").append(dayOfMonth).toString();
        dateAcceptance.setText(date);
        try {
            invoice.date = Helper.getFormatter().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            Toast.makeText(
//                    MainActivity.this,
//                    String.valueOf(year) + "-" + String.valueOf(monthOfYear)
//                            + "-" + String.valueOf(dayOfMonth),
//                    Toast.LENGTH_LONG).show();
//        }
//    }
}
