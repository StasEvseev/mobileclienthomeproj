package com.example.stas.homeproj.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.stas.homeproj.InvoicesActivity;
import com.example.stas.homeproj.Session;
import com.example.stas.homeproj.db.dao.AcceptanceHolder;
import com.example.stas.homeproj.db.dao.CommodityHolder;
import com.example.stas.homeproj.db.dao.InvoiceHolder;
import com.example.stas.homeproj.db.dao.InvoiceItemHolder;
import com.example.stas.homeproj.db.dao.PriceHolder;
import com.example.stas.homeproj.db.dao.ProviderHolder;
import com.example.stas.homeproj.db.dao.model.Acceptance;
import com.example.stas.homeproj.db.dao.model.Commodity;
import com.example.stas.homeproj.db.dao.model.Invoice;
import com.example.stas.homeproj.db.dao.model.InvoiceItem;
import com.example.stas.homeproj.db.dao.model.Price;
import com.example.stas.homeproj.db.dao.model.Provider;
import com.example.stas.homeproj.library.RestApiHelper;
import com.example.stas.homeproj.models.AcceptanceBuyApi;
import com.example.stas.homeproj.models.CommodityBuyApi;
import com.example.stas.homeproj.models.InvoiceBuyApi;
import com.example.stas.homeproj.models.InvoiceItemBuyApi;
import com.example.stas.homeproj.models.PriceBuyApi;
import com.example.stas.homeproj.models.ProviderBuyApi;
import com.example.stas.homeproj.provider.MainContentProvider;
import com.example.stas.homeproj.resources.IAcceptanceRestAPI;
import com.example.stas.homeproj.resources.ICommodityRestAPI;
import com.example.stas.homeproj.resources.IInvoiceItemRestAPI;
import com.example.stas.homeproj.resources.IInvoiceRestAPI;
import com.example.stas.homeproj.resources.IPriceRestAPI;
import com.example.stas.homeproj.resources.IProviderRestApi;

import java.io.IOException;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    AccountManager mAccountManager;

    public final static String LOG = SyncAdapter.class.getName();

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        Log.d(LOG, "2 argument");
        mAccountManager = AccountManager.get(context);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        try {
            String authToken = AccountManager.get(getContext()).blockingGetAuthToken(account,
                    Session.TOKEN_TYPE, true);

            if (authToken == null || "".equals(authToken)) {
                return;
            }

            Log.d(LOG, "SYNC! SYNC! SYNC! = " + authToken);

            Context context = getContext();

            IPriceRestAPI priceRestAPI = RestApiHelper.createResource(IPriceRestAPI.class,
                    context, authToken);

            PriceBuyApi.PriceItemsBuyApi priceItemsBuyApi = priceRestAPI.priceSync();

            for (PriceBuyApi priceBuyApi: priceItemsBuyApi.items) {

                Price price = new Price();
                price.commodity_id = priceBuyApi.commodity_id;
                price.NDS = priceBuyApi.NDS;
                price.price_prev = priceBuyApi.price_prev;
                price.price_post = priceBuyApi.price_post;
                price.number_local = priceBuyApi.number_local;
                price.number_global = priceBuyApi.number_global;
                price.price_gross = priceBuyApi.price_gross;
                price.price_retail = priceBuyApi.price_retail;
                price.date_from = priceBuyApi.date_from;
                price.id_buy_api = priceBuyApi.id;

                context.getContentResolver().insert(MainContentProvider.CONTENT_URI_PRICE, PriceHolder.toCursor(price));
            }

            ICommodityRestAPI commodityRestAPI = RestApiHelper.createResource(ICommodityRestAPI.class,
                    context, authToken);

            CommodityBuyApi.CommodityItemsBuyApi commodityItemsBuyApi = commodityRestAPI.commoditySync();

            for (CommodityBuyApi commodityBuyApi: commodityItemsBuyApi.items) {
                Commodity commodity = new Commodity();
                commodity.category = commodityBuyApi.category;
                commodity.name = commodityBuyApi.name;
                commodity.id_buy_api = commodityBuyApi.id;

                context.getContentResolver().insert(MainContentProvider.CONTENT_URI_COMMODITY,
                        CommodityHolder.toCursor(commodity));
            }

            IAcceptanceRestAPI acceptanceRestAPI = RestApiHelper.createResource(IAcceptanceRestAPI.class,
                    context, authToken);

            AcceptanceBuyApi.AcceptanceItemsBuyApi acceptanceItemsBuyApi = acceptanceRestAPI.acceptanceSync();

            for (AcceptanceBuyApi acceptanceBuyApi: acceptanceItemsBuyApi.items) {
                Acceptance acceptance = new Acceptance();
                acceptance.invoice_id = acceptanceBuyApi.invoice_id;
                acceptance.date = acceptanceBuyApi.date;
                acceptance.id_buy_api = acceptanceBuyApi.id;

                context.getContentResolver().insert(MainContentProvider.CONTENT_URI_ACCEPTANCE,
                        AcceptanceHolder.toCursor(acceptance));
            }

            IProviderRestApi providerRestApi = RestApiHelper.createResource(IProviderRestApi.class,
                    context, authToken);

            ProviderBuyApi.ProviderItemsBuyApi providerItemsBuyApi = providerRestApi.providerSync();

            for (ProviderBuyApi providerBuyApi: providerItemsBuyApi.items) {
                Provider providerInst = new Provider();

                providerInst.name = providerBuyApi.name;
                providerInst.address = providerBuyApi.address;
                providerInst.id_buy_api = providerBuyApi.id;

                context.getContentResolver().insert(MainContentProvider.CONTENT_URI_PROVIDER,
                        ProviderHolder.toCursor(providerInst));
            }


            IInvoiceRestAPI invoiceRestAPI = RestApiHelper.createResource(IInvoiceRestAPI.class, context, authToken);

            InvoiceBuyApi.InvoiceItemsBuyApi invoiceItemsBuyApi = invoiceRestAPI.invoiceSync();

            for (InvoiceBuyApi invoiceBuyApi: invoiceItemsBuyApi.items) {
                Invoice invoice = new Invoice();
                invoice.id_buy_api = invoiceBuyApi.id;
                invoice.provider_id = invoiceBuyApi.provider_id;
                invoice.date = invoiceBuyApi.date;
                invoice.number = invoiceBuyApi.number;

                context.getContentResolver().insert(MainContentProvider.CONTENT_URI_INVOICE,
                        InvoiceHolder.toCursor(invoice));
            }

            IInvoiceItemRestAPI invoiceItemRestAPI = RestApiHelper.createResource(IInvoiceItemRestAPI.class,
                    context, authToken);

            Cursor curInvoice = context.getContentResolver().query(MainContentProvider.CONTENT_URI_INVOICE, null,
                    null, null, null);

            if (curInvoice != null) {
                while (curInvoice.moveToNext()) {
                    Invoice invoice = new Invoice();
                    InvoiceHolder.fromCursor(curInvoice, invoice);
                    InvoiceItemBuyApi.InvoiceItemItemsBuyApi invoiceItemItemsBuyApi = invoiceItemRestAPI.itemsSync(
                            invoice.id_buy_api);

                    for (InvoiceItemBuyApi invoiceItemBuyApi: invoiceItemItemsBuyApi.items) {
                        InvoiceItem invoiceItem = new InvoiceItem();
                        invoiceItem.invoice_id = invoice.id_buy_api;
                        invoiceItem.commodity_id = invoiceItemBuyApi.commodity_id;
                        invoiceItem.price_id = invoiceItemBuyApi.price_id;
                        invoiceItem.fullname = invoiceItemBuyApi.full_name;
                        invoiceItem.number_global = invoiceItemBuyApi.number_global;
                        invoiceItem.number_local = invoiceItemBuyApi.number_local;
                        invoiceItem.count = invoiceItemBuyApi.count;
                        invoiceItem.placer = invoiceItemBuyApi.placer;
                        invoiceItem.count_whole_pack = invoiceItemBuyApi.count_whole_pack;
                        Log.d("SYNCADAPTER", "factcount - " + invoiceItemBuyApi.factcount);
                        Log.d("SYNCADAPTER", "barcode - " + invoiceItemBuyApi.barcode);
                        if (invoiceItemBuyApi.factcount != null) {
                            invoiceItem.factcount = Integer.parseInt(invoiceItemBuyApi.factcount);
                        }
//                        if (invoiceItemBuyApi.barcode != null) {
                            invoiceItem.barcode = invoiceItemBuyApi.barcode;
//                        }

                        context.getContentResolver().insert(MainContentProvider.CONTENT_URI_INVOICEITEM, InvoiceItemHolder.toCursor(invoiceItem));
                    }
                }
            }



//            InvoiceSync.sync(context, authToken);

//            InvoiceSync.sync(context, authToken);
//
//            List<Invoice> list = InvoiceSync.getInvoices(context);
//
//            for (Invoice item : list) {
//                InvoiceItemSync.sync(context, item, authToken);
//            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.btn_star_big_off)
                    .setContentTitle("HomeProj")
                    .setContentText("Данные синхронизованы!");
            Intent resultIntent = new Intent(context, InvoicesActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(InvoicesActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = builder.build();
            notify.tickerText = "";
            mNotificationManager.notify(1, notify);

        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        }


    }
}