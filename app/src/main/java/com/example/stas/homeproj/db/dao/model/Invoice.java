package com.example.stas.homeproj.db.dao.model;

import android.content.Context;

import com.example.stas.homeproj.db.dao.AcceptanceHolder;
import com.example.stas.homeproj.db.dao.ProviderHolder;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.provider.helper.ProviderContent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 27.11.14.
 */
public class Invoice extends BaseSyncModel {
    public int provider_id;
    public String number;
    public Date date;
    public String emails;

    public Invoice() {
//        is_sync = true;
    }

    public String getDateToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = "";
        if (date != null) {
            return sdf.format(this.date);
        }
        return result;
    }

    @Override
    public String toString() {
        return number;
    }

    public String getProviderString(Context context) {
        Provider provider = (Provider)ProviderContent.getById(context, MainContentProvider.CONTENT_URI_PROVIDER,
                ProviderHolder.COL_ID, provider_id, Provider.class, ProviderHolder.class);
        if (provider != null) {
            return provider.name;
        } else {
            return "";
        }
    }

    public boolean is_handle(Context context) {
        Acceptance acc = getAcceptance(context);
        if (acc != null) {
            return true;
        }
        return false;
    }

    public Acceptance getAcceptance(Context context) {
        return (Acceptance) ProviderContent.getById(context, MainContentProvider.CONTENT_URI_ACCEPTANCE,
                AcceptanceHolder.COL_INVOICE_ID, id, Acceptance.class, AcceptanceHolder.class);
    }

    public Acceptance createAcceptance(Context context) {
        if(is_handle(context)) {
            context.getContentResolver().delete(MainContentProvider.CONTENT_URI_ACCEPTANCE,
                    AcceptanceHolder.COL_INVOICE_ID + "=?", new String[] { String.valueOf(id) });
        }
        Acceptance acceptance = new Acceptance();
        acceptance.invoice_id = id;
        acceptance.date = new Date();
        context.getContentResolver().insert(MainContentProvider.CONTENT_URI_ACCEPTANCE, AcceptanceHolder.toCursor(acceptance));
        return getAcceptance(context);
    }
}
