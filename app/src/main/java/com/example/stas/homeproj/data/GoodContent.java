package com.example.stas.homeproj.data;

import android.util.SparseArray;

import com.example.stas.homeproj.models.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author StasEvseev
 * Класс для хранения товара из накладной
 */
public class GoodContent {

    private static List<Good> goodList = new ArrayList<Good>();

    private static SparseArray<Good> goodMap = new SparseArray<Good>();

    public static void addItem(Good item) {
        goodList.add(item);
        goodMap.put(item.id, item);
    }

    public static Good getItem(int key) {
        return goodMap.get(key);
    }

}
