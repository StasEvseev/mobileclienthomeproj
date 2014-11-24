package com.example.stas.homeproj;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.stas.homeproj.data.GoodContent;
import com.example.stas.homeproj.db.dao.GoodHolder;
import com.example.stas.homeproj.db.dao.GoodLocalHolder;
import com.example.stas.homeproj.models.GoodBuyApi;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.provider.GoodContentProvider;
import com.example.stas.homeproj.provider.GoodLocalContentProvider;
import com.example.stas.homeproj.provider.helper.Provider;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link GoodListActivity}
 * in two-pane mode (on tablets) or a {@link GoodDetailActivity}
 * on handsets.
 */
public class GoodDetailFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    //имя аргумента от активити для отображения
    public static final String ARG_ITEM_ID = "item_id";

    public static final String LOG_TAG = GoodDetailFragment.class.getName();

    /**
     * Моделька товара
     */
    private GoodLocal mItem;

    private CallbacksSave defaultCallback = new CallbacksSave() {

        @Override
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }
    };

    private CallbacksSave dCallback = defaultCallback;

    public interface CallbacksSave {
        /**
         * Колбэк для кнопки сохранить
         */

        public void onSuccess();

        public void onFailure();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GoodDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate");

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Log.d(LOG_TAG, "onCreate is ARGUMENT");
            //Извлекаем товар по переданному id в аргументы
//            mItem = GoodContent.getItem(getArguments().getInt(ARG_ITEM_ID));

//            ContentResolver resolver = getActivity().getContentResolver();
            Context context = getActivity().getApplicationContext();

            GoodLocal goodLocal= (GoodLocal)Provider.getById(context, GoodLocalContentProvider.CONTENT_URI,
                    GoodLocalHolder.COL_ID, getArguments().getInt(ARG_ITEM_ID), GoodLocal.class, GoodLocalHolder.class);

            GoodBuyApi goodBuyApi = (GoodBuyApi)Provider.getById(context, GoodContentProvider.CONTENT_URI,
                    GoodHolder.COL_ID, goodLocal.id_good_buy_api, GoodBuyApi.class, GoodHolder.class);

//            goodLocal.good = goodBuyApi;
            goodLocal.setGood(goodBuyApi);

            mItem = goodLocal;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_good_detail, container, false);

        if (mItem != null) {
            Log.d(LOG_TAG, "onCreateView mITEM");
            ((TextView) rootView.findViewById(R.id.good_fullname)).setText(mItem.getFullname());
            ((TextView) rootView.findViewById(R.id.good_count)).setText(String.valueOf(mItem.getCount()));
            if(mItem.factCount != 0) {
                ((EditText) rootView.findViewById(R.id.good_factcount)).setText(String.valueOf(mItem.factCount));
            }
            if(mItem.barcode != 0) {
                ((EditText) rootView.findViewById(R.id.good_barcode)).setText(String.valueOf(mItem.barcode));
            }
        }

        rootView.findViewById(R.id.good_item_save).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        dCallback = (CallbacksSave)activity;

        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        dCallback = defaultCallback;

        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        Activity act = getActivity();

        EditText gfc_field = (EditText)act.findViewById(R.id.good_factcount);
        EditText bc_field = (EditText)act.findViewById(R.id.good_barcode);

        gfc_field.setError(null);

        String rawFactCount = gfc_field.getText().toString();
        String rawBarcode = bc_field.getText().toString();

        if(TextUtils.isEmpty(rawFactCount)) {
            gfc_field.setError("Поле обязательное");
            dCallback.onFailure();
        } else {

            mItem.factCount = Integer.parseInt(rawFactCount);

            if (!TextUtils.isEmpty(rawBarcode)) {
                mItem.barcode = Integer.parseInt(rawBarcode);
            }

//            mItem.barcode = Integer.parseInt(rawBarcode);
//            mItem.factCount = Integer.parseInt(rawFactCount);
            mItem.is_sync = false;
            getActivity().getContentResolver().update(GoodLocalContentProvider.CONTENT_URI,
                    GoodLocalHolder.toCursor(mItem), GoodLocalHolder.COL_ID + "=?", new String[] { String.valueOf(mItem.id) });
//            GoodContent.update(mItem, rawFactCount, rawBarcode);
            dCallback.onSuccess();
        }
    }
}
