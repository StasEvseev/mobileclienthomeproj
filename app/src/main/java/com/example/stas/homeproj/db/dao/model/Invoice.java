package com.example.stas.homeproj.db.dao.model;

import java.util.Date;

/**
 * Created by user on 27.11.14.
 */
public class Invoice extends BaseSyncModel {
    public int provider_id;
    public String number;
    public Date date;
}
