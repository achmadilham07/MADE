package com.example.made.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.made.R;
import com.example.made.notif.DailyAlarmReceiver;
import com.example.made.notif.ReleaseAlarmReceiver;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    public static final int KEY_HOUR_DAILY = 7;
    public static final int KEY_HOUR_RELEASE = 8;
    public static final int KEY_MINUTE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_settings));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        SwitchPreferenceCompat releaseStat, dailyStat;
        private AlarmManager dailyAlarm, releaseAlarm;
        private PendingIntent dailyPending, releasePending;

        private SwitchPreference.OnPreferenceChangeListener releaseLis = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isDataTrafficOn = (Boolean) newValue;
                if (isDataTrafficOn) {
                    sendReleaseNotif();
                } else {
                    cancelNotification(releaseAlarm, releasePending);
                }
                return true;
            }
        };

        private SwitchPreference.OnPreferenceChangeListener dailyLis = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isDataTrafficOn = (Boolean) newValue;
                if (isDataTrafficOn) {
                    sendDailyNotif();
                } else {
                    cancelNotification(dailyAlarm, dailyPending);
                }
                return true;
            }
        };

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            dailyAlarm = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            releaseAlarm = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

            releaseStat = findPreference("release");
            dailyStat = findPreference("daily");
            releaseStat.setOnPreferenceChangeListener(releaseLis);
            dailyStat.setOnPreferenceChangeListener(dailyLis);
        }

        private void sendReleaseNotif() {
            Toast.makeText(getContext(), "Release Alarm ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, KEY_HOUR_RELEASE);
            calendar.set(Calendar.MINUTE, KEY_MINUTE);
            calendar.set(Calendar.SECOND, 0);

            Intent myIntent = new Intent(getContext(), ReleaseAlarmReceiver.class);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }

            releasePending = PendingIntent.getBroadcast(getContext(), 100, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            releaseAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, releasePending);
        }

        private void sendDailyNotif() {
            Toast.makeText(getContext(), "Daily Alarm ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, KEY_HOUR_DAILY);
            calendar.set(Calendar.MINUTE, KEY_MINUTE);
            calendar.set(Calendar.SECOND, 0);

            Intent myIntent = new Intent(getContext(), DailyAlarmReceiver.class);
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }
            dailyPending = PendingIntent.getBroadcast(getContext(), 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            dailyAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dailyPending);
        }

        private void cancelNotification(AlarmManager alarm, PendingIntent pendingIntent) {
            if (pendingIntent != null)
                alarm.cancel(pendingIntent);
            Toast.makeText(getContext(), "Alarm OFF", Toast.LENGTH_SHORT).show();
        }
    }
}