package com.liamgoodwin.beforeidie;



import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

//        FragmentManager fm = getFragmentManager();
//
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.content, new SettingsPrefFragment());
//        ft.commit();

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsPrefFragment()).commit();

        return view;
    }

    public static class SettingsPrefFragment extends PreferenceFragmentCompat {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferencs);
        }

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {

        }
    }
}