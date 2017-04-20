package com.liamgoodwin.beforeidie;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Used to load the new Settings Fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsPrefFragment()).commit();
    }

    //creating the Settings Fragment and populating it with the settings view called preferences
    public static class SettingsPrefFragment extends PreferenceFragment {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferencs);
        }
    }
}
