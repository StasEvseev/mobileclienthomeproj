package com.example.stas.homeproj.db.dao;

import java.text.SimpleDateFormat;

/**
 * Created by user on 27.11.14.
 */
public class Helper {

    private static SimpleDateFormat formatter;

    public static SimpleDateFormat getFormatter() {
        if (formatter == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        return formatter;
    }

}
