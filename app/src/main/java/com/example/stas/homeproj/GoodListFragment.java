package com.example.stas.homeproj;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.stas.homeproj.db.dao.GoodHolder;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.provider.GoodContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link GoodDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class GoodListFragment extends ListFragment {

    private List<GoodBuyApi> lgood;

//    private CursorAdapter listAdapter;

//    private HashMap<Integer, GoodBuyApi> keyV;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(int id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GoodListFragment() {
    }

    public void loadGood(int id) {

        Log.d("loadGood", String.valueOf(id));

        Cursor curGood = getActivity().getContentResolver().query(
                GoodContentProvider.CONTENT_URI, null,
                GoodHolder.COL_INVOICE_ID + " = ?", new String[] { String.valueOf(id) }, null, null);

        if(curGood != null) {
            while(curGood.moveToNext()) {
                lgood.add(GoodHolder.fromCursor(curGood));
            }
        }

//        IInvoiceItemRestAPI good_api = RestApiHelper.createResource(IInvoiceItemRestAPI.class, getActivity());
//
//        good_api.goods(id, new Callback<GoodsBuyApi>() {
//            @Override
//            public void success(GoodsBuyApi lgoods, Response response) {
//                Log.d("DEBUGGG!!!", "SUCCESS");
//                for (int i = 0; i < lgoods.items.size(); i++) {
//                    GoodBuyApi goodBuyApi = lgoods.items.get(i);
//                    lgood.add(goodBuyApi);
//
//                    GoodContent.addItem(new GoodLocal(goodBuyApi));
////                    keyV.put(goodBuyApi.id, goodBuyApi);
//                }
//
//                setListAdapter(new ArrayAdapter<GoodBuyApi>(
//                        getActivity(),
//                        android.R.layout.simple_list_item_activated_1,
//                        lgood));
//
////                adapter.addAll(lgood);
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                Log.d("DEBUGGG!!!", retrofitError.toString());
//            }
//        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lgood = new ArrayList<GoodBuyApi>();

//        listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, new String());

//        keyV = new HashMap<Integer, GoodBuyApi>();

        // TODO: replace with a real list adapter.
        setListAdapter(new ArrayAdapter<GoodBuyApi>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                lgood));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        GoodBuyApi goodBuyApi = lgood.get(position);

        mCallbacks.onItemSelected(goodBuyApi.id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
