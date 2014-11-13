package com.example.stas.homeproj.data;

import android.text.TextUtils;
import android.util.SparseArray;

import com.example.stas.homeproj.models.Good;
import com.example.stas.homeproj.models.GoodBuyApi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author StasEvseev
 * Класс для хранения товара из накладной
 */
public class GoodContent {

    private static List<Good> goodBuyApiList = new ArrayList<Good>();

    private static SparseArray<Good> goodMap = new SparseArray<Good>();

    public static void addItem(Good item) {
        goodBuyApiList.add(item);
        goodMap.put(item.getId(), item);
    }

    public static Good getItem(int key) {
        return goodMap.get(key);
    }

    public static boolean update(Good good, String rawFactCount, String rawBarcode) {
        Good gold = GoodContent.getItem(good.getId());
        gold.factCount = Integer.parseInt(rawFactCount);

        if (!TextUtils.isEmpty(rawBarcode)) {
            gold.barcode = Integer.parseInt(rawBarcode);
        }
        return true;
    }
}
