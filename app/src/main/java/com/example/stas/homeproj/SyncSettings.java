package com.example.stas.homeproj;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by user on 24.11.14.
 */
public class SyncSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String KEY_AUTO_SYNC = "com.example.stas.homepro.KEY_AUTO_SYNC";

    private static final String KEY_AUTO_SYNC_INTERVAL = "com.example.stas.homeproj.KEY_AUTO_SYNC_INTERVAL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sync_prefs);
        final ListPreference interval = (ListPreference) getPreferenceManager()
                .findPreference(KEY_AUTO_SYNC_INTERVAL);
        interval.setSummary(interval.getEntry());
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        Log.d("SyncSettings", "onSharedPreferenceChanged");
        ContentResolver.setSyncAutomatically(MyApplication.sAccount, MyApplication.AUTHORITY, true);
        if (TextUtils.equals(KEY_AUTO_SYNC, key)) {
            if (prefs.getBoolean(key, false)) {
                final long interval = Long.parseLong(prefs.getString(
                        KEY_AUTO_SYNC_INTERVAL,
                        getString(R.string.auto_sync_interval_default)
                ));
                ContentResolver.addPeriodicSync(MyApplication.sAccount, MyApplication.AUTHORITY, Bundle.EMPTY, interval);
            } else {
                ContentResolver.removePeriodicSync(MyApplication.sAccount, MyApplication.AUTHORITY, new Bundle());
            }
        } else if (TextUtils.equals(KEY_AUTO_SYNC_INTERVAL, key)) {

            final ListPreference interval = (ListPreference) getPreferenceManager().findPreference(key);
            interval.setSummary(interval.getEntry());
            Log.d("SyncSettings", "getValue - " + interval.getValue());
            ContentResolver.addPeriodicSync(
                    MyApplication.sAccount, MyApplication.AUTHORITY,
                    Bundle.EMPTY, Long.parseLong(interval.getValue())
            );
        }
    }

}
