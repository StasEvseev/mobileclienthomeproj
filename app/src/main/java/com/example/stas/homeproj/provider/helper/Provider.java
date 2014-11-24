package com.example.stas.homeproj.provider.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.stas.homeproj.db.dao.GoodLocalHolder;
import com.example.stas.homeproj.models.GoodLocal;
import com.example.stas.homeproj.provider.GoodLocalContentProvider;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Stanislav on 24.11.2014.
 */
public class Provider {

    public static Object getById(Context context, Uri uri, String col, int id, Class<?> cl, Class<?> holder) {
        Cursor cur = context.getContentResolver().query(
                uri, null, col + "=?",
                new String[]{ String.valueOf(id) }, null);

        Object inst = null;
        try {
            inst = cl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(cur!=null) {
            while (cur.moveToNext()) {
                try {
                    inst = holder.getMethod("fromCursor", Cursor.class).invoke(null, cur);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
//                inst = GoodLocalHolder.fromCursor(cur);
            }
        }

        cur.close();

        return inst;
    }

}
