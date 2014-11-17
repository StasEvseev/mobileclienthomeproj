package com.example.stas.homeproj;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.stas.homeproj.data.GoodContent;
import com.example.stas.homeproj.models.GoodLocal;

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

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            //Извлекаем товар по переданному id в аргументы
            mItem = GoodContent.getItem(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_good_detail, container, false);

        if (mItem != null) {
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
            GoodContent.update(mItem, rawFactCount, rawBarcode);
            dCallback.onSuccess();
        }


    }
}
