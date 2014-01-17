package com.sciaps.android.libs.mainsettings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sciaps.android.libs.mainsettings.utils.LibzSettings;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String ACTION_PREFS_ONE = "One";
    private static final String FACTORY_MODE_PASSWORD ="123" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LibzSettings ls = new LibzSettings(getApplicationContext());


        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("libz_main");
        prefMgr.setSharedPreferencesMode(MODE_WORLD_READABLE);


        addPreferencesFromResource(R.xml.prefs);

        ProgressBarPref prefBat = (ProgressBarPref) findPreference("pref_key_battery_lev");
        prefBat.setProgress(99);
        prefBat.setLabel("99%");

        ProgressBarPref prefArg = (ProgressBarPref) findPreference("pref_key_argon");
        prefArg.setProgress(91);
        prefArg.setLabel("91%");

        CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md2");
        if (prefFact.isChecked()) {

            prefFact.setIcon(R.drawable.ic_check);

        }else {
            prefFact.setIcon(android.R.drawable.ic_delete);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);

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


                    if (changeFMPref){
                        //firstTime

                    prefFact.setChecked(false);
                    showPasswordDialog();

                    }else {
                        changeFMPref =true;
                    }
                }else {
                    prefFact.setIcon(android.R.drawable.ic_delete);

                }


        }
        LibzSettings.writeSettingsXml();
    }
    private boolean changeFMPref = true;
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
                if (value.equals(FACTORY_MODE_PASSWORD)) {
                    changeFMPref = false;

                    CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md2");
                    prefFact.setChecked(true);

                    prefFact.setIcon(R.drawable.ic_check);

                    return;


                }else {
                    Toast.makeText(getApplicationContext(),"Wrong Password, No Permission!",Toast.LENGTH_LONG).show();
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
