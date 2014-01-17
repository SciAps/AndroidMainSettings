package com.sciaps.android.libs.mainsettings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sciaps.android.libs.mainsettings.utils.HardwareStatus;
import com.sciaps.android.libs.mainsettings.utils.LibzSettings;
import com.sciaps.android.libs.mainsettings.utils.ProgressBarPref;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String ACTION_PREFS_ONE = "One";
    private static final String FACTORY_MODE_PASSWORD ="123" ;
    private static final int FAHRENHEIT = 0;
    private static final int CELSIUS = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LibzSettings ls = new LibzSettings(getApplicationContext());


        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("libz_main");
        prefMgr.setSharedPreferencesMode(MODE_WORLD_READABLE);


        addPreferencesFromResource(R.xml.prefs);

        getHardwareStats();




        CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md");
        if (prefFact.isChecked()) {

            prefFact.setIcon(R.drawable.ic_check);

        }else {
            prefFact.setIcon(android.R.drawable.ic_delete);

        }

    }

    private void getHardwareStats() {
        HardwareStatus hs = new HardwareStatus();

        SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();

        int temprature = 0;
        try {
            temprature = Integer.parseInt(prefs.getString("pref_key_temp", FAHRENHEIT+""));
            String s = temprature==0?("°F " +hs.getTempF()):("°C " +hs.getTempC());

            findPreference("pref_key_temp").setSummary(temprature == 0 ? "°F" : "°C");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Preference statTemp = findPreference("stats_key_temp");

        String s = temprature==0?("°F " +hs.getTempF()):("°C " +hs.getTempC());


        statTemp.setSummary(s);



        ProgressBarPref prefBat = (ProgressBarPref) findPreference("stats_key_battery_lev");
        prefBat.setProgress(hs.getBatteryLevel());

        findPreference("stats_key_battery_state") .setSummary(hs.getBatteryStatus());

        prefBat.setLabel(hs.getBatteryLevel()+"%");

        ProgressBarPref prefArg = (ProgressBarPref) findPreference("stats_key_argon");
        prefArg.setProgress(hs.getArgonLevel());
        prefArg.setLabel(hs.getArgonLevel()+"%");

      
        int[] xyz =hs.getXYZStatus();
        findPreference("stats_key_x").setSummary(xyz[0]+"");
        findPreference("stats_key_y").setSummary(xyz[1]+"");
        findPreference("stats_key_z").setSummary(xyz[2]+"");


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


        if (key.equals("pref_key_factory_md")) {


                CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md");

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


        }else if(key.equals("pref_key_temp")){
            int temprature = 0;
            HardwareStatus hs = new HardwareStatus();
            try {
                temprature = Integer.parseInt(sharedPreferences.getString(key, FAHRENHEIT+""));
              
                String s = temprature==0?("°F " +hs.getTempF()):("°C " +hs.getTempC());

                findPreference("pref_key_temp").setSummary(temprature == 0 ? "°F" : "°C");

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Preference statTemp = findPreference("stats_key_temp");

            String s = temprature==0?("°F " +hs.getTempF()):("°C " +hs.getTempC());


            statTemp.setSummary(s);

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

                    CheckBoxPreference prefFact = (CheckBoxPreference) findPreference("pref_key_factory_md");
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
