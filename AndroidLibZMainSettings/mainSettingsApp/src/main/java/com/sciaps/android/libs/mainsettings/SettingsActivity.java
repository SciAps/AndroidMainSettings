package com.sciaps.android.libs.mainsettings;

import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String ACTION_PREFS_ONE = "One";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        ProgressBarPref prefBat = (ProgressBarPref) findPreference("pref_key_battery_lev");
        prefBat.setProgress(99);
        prefBat.setLabel("99%");

        ProgressBarPref prefArg = (ProgressBarPref) findPreference("pref_key_argon");
        prefArg.setProgress(91);
        prefArg.setLabel("91%");

    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_key_factory_md2")) {

            CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md2");

            if (prefFact.isChecked()) {
                prefFact.setChecked(false);
                showPasswordDialog();
            }


        }
    }

    private void showPasswordDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Enter Password");
        alert.setMessage("");
        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setPadding(40,40,40,40);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                // Do something with value!
                if (value.equals("123")) {

                    CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md2");
                    prefFact.setChecked(true);
                    prefFact.setIcon(R.drawable.ic_action_brightness_auto);
                    return;


                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                return;

            }
        });

        alert.show();

    }


}
