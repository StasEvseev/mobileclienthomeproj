package com.example.stas.homeproj.library;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

import java.util.Calendar;

/**
 * Created by user on 28.11.14.
 */
public class AlertHelper {

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calender = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, listener, calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

        if (Build.VERSION.SDK_INT >= 11) {
            dialog.getDatePicker().setCalendarViewShown(false);
        }

        dialog.show();
    }
}
