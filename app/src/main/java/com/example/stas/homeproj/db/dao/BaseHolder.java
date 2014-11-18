package com.example.stas.homeproj.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 18.11.14.
 */
public interface BaseHolder<T> {

    ContentValues toCursor(T obj);

    T fromCursor(Cursor cur);
}
