package com.example.stas.homeproj.data;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

import com.example.stas.homeproj.models.InvoiceBuyApi;

/**
 * @author StasEvseev
 * Класс для хранения накладных
 */
public class InvoiceContent {
    private static List<InvoiceBuyApi> invoiceBuyApiList = new ArrayList<InvoiceBuyApi>();

    private static SparseArray<InvoiceBuyApi> invoiceMap = new SparseArray<InvoiceBuyApi>();

    public static void addItem(InvoiceBuyApi item) {
        invoiceBuyApiList.add(item);
        invoiceMap.put(item.id, item);
    }

    public static InvoiceBuyApi getItem(int key) {
        return invoiceMap.get(key);
    }
}
