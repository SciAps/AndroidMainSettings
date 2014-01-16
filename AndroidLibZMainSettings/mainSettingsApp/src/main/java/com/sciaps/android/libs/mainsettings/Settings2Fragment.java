package com.sciaps.android.libs.mainsettings;



import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;


public class Settings2Fragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs2);

      ProgressBarPref prefBat = (ProgressBarPref) findPreference("pref_key_battery_lev");
        prefBat.setProgress(99);
        prefBat.setLabel("99%");

        ProgressBarPref prefArg = (ProgressBarPref) findPreference("pref_key_argon");
        prefArg.setProgress(91);
        prefArg.setLabel("91%");
    }






}
