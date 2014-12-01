package com.example.stas.homeproj.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Stanislav on 27.11.2014.
 */
public class AcceptanceBuyApi {
    public int id;
    public int invoice_id;
    public Date date;

    public class AcceptanceItemsBuyApi {
        public List<AcceptanceBuyApi> items;
    }
}
