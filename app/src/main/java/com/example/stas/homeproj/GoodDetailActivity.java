package com.example.stas.homeproj;

import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;

import com.example.stas.homeproj.db.dao.InvoiceItemHolder;
import com.example.stas.homeproj.db.dao.model.InvoiceItem;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.provider.helper.ProviderContent;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GoodListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link GoodDetailFragment}.
 */
public class GoodDetailActivity extends Activity implements GoodDetailFragment.CallbacksSave {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            int id_good = getIntent().getIntExtra(GoodDetailFragment.ARG_ITEM_ID, 0);

            InvoiceItem invoiceItem = (InvoiceItem) ProviderContent.getById(getApplicationContext(),
                    MainContentProvider.CONTENT_URI_INVOICEITEM, InvoiceItemHolder.COL_ID, id_good, InvoiceItem.class,
                    InvoiceItemHolder.class);

//            GoodLocal goodLocal= (GoodLocal)ProviderContent.getById(getApplicationContext(), GoodLocalContentProvider.CONTENT_URI,
//                    GoodLocalHolder.COL_GOOD_ID, goodBuyApi.id, GoodLocal.class, GoodLocalHolder.class);

//            goodLocal.setGood(goodBuyApi);
//            goodLocal.good = goodBuyApi;
            setTitle(invoiceItem.toString());
            Bundle arguments = new Bundle();
            arguments.putInt(GoodDetailFragment.ARG_ITEM_ID,
                    invoiceItem.id);
            GoodDetailFragment fragment = new GoodDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.good_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
//            NavUtils.navigateUpTo(this, new Intent(this, GoodListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onFailure() {

    }
}
