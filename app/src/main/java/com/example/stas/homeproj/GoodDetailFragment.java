package com.example.stas.homeproj;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.stas.homeproj.data.GoodContent;
import com.example.stas.homeproj.models.Good;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link GoodListActivity}
 * in two-pane mode (on tablets) or a {@link GoodDetailActivity}
 * on handsets.
 */
public class GoodDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    //имя аргумента от активити для отображения
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Моделька товара
     */
    private Good mItem;

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
            ((TextView) rootView.findViewById(R.id.good_fullname)).setText(mItem.full_name);
            ((TextView) rootView.findViewById(R.id.good_count)).setText(String.valueOf(mItem.count));
        }

        return rootView;
    }
}
