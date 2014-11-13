package com.example.stas.homeproj.data;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

import com.example.stas.homeproj.models.Invoice;

/**
 * @author StasEvseev
 * Класс для хранения накладных
 */
public class InvoiceContent {
    private static List<Invoice> invoiceList = new ArrayList<Invoice>();

    private static SparseArray<Invoice> invoiceMap = new SparseArray<Invoice>();

    public static void addItem(Invoice item) {
        invoiceList.add(item);
        invoiceMap.put(item.id, item);
    }

    public static Invoice getItem(int key) {
        return invoiceMap.get(key);
    }
}
