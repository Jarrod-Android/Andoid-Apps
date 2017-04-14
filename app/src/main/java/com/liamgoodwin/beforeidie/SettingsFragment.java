package com.liamgoodwin.beforeidie;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        //getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsPrefFragment()).commit();

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public static class SettingsPrefFragment extends PreferenceFragment {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferencs);
        }
    }
}