package com.example.stas.homeproj.data;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.stas.homeproj.models.GoodLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author StasEvseev
 * Класс для хранения товара из накладной
 */
public class GoodContent {

    private static List<GoodLocal> goodLocalBuyApiList = new ArrayList<GoodLocal>();

    private static SparseArray<GoodLocal> goodMap = new SparseArray<GoodLocal>();

    public static void addItem(GoodLocal item) {
        goodLocalBuyApiList.add(item);
        goodMap.put(item.getId(), item);
        Log.d("GoodLOCAL", String.valueOf(goodLocalBuyApiList.size()));
    }

    public static GoodLocal getItem(int key) {
        return goodMap.get(key);
    }

    public static boolean update(GoodLocal goodLocal, String rawFactCount, String rawBarcode) {
        GoodLocal gold = GoodContent.getItem(goodLocal.getId());
        gold.factCount = Integer.parseInt(rawFactCount);

        if (!TextUtils.isEmpty(rawBarcode)) {
            gold.barcode = Integer.parseInt(rawBarcode);
        }
        return true;
    }

    public static List<GoodLocal> getItems() {
        Log.d("GOODLOCAL", String.valueOf(goodLocalBuyApiList.size()));
        return goodLocalBuyApiList;
    }
}
