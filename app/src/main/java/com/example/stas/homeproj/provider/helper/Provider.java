package com.example.stas.homeproj.provider.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.reflect.InvocationTargetException;

/**
 * @author StasEvseev
 * Класс помощник различных вспомогательных функций для работы с провайдерами.
 */
public class Provider {

    public static Object getById(Context context, Uri uri, String col, int id, Class<?> cl, Class<?> holder) {
        Cursor cur = context.getContentResolver().query(
                uri, null, col + "=?",
                new String[]{ String.valueOf(id) }, null);

        Object inst = null;

        if(cur!=null) {
            try {
                inst = cl.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            while (cur.moveToNext()) {
                try {
                    holder.getMethod("fromCursor", Cursor.class, cl).invoke(null, cur, inst);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            cur.close();
        }

        return inst;
    }

}
