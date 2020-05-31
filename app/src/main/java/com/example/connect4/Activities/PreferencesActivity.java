package com.example.connect4.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.example.connect4.R;


public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new OpcionesFragment()).commit();

    }

    public static class OpcionesFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstaceState) {
            super.onCreate(savedInstaceState);
            addPreferencesFromResource(R.xml.preferences);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        }
    }
}
