package com.randywu.fancyfactory;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingFragment extends PreferenceFragment {

    private static final String TAG = SettingFragment.class.getSimpleName();

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        setupSimplePreferencesScreen();
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen() {
        // Add 'general' preferences.
        addPreferencesFromResource(R.xml.pref_general);

        // Add 'notifications' preferences, and a corresponding header.
        PreferenceCategory fakeHeader = new PreferenceCategory(this.getActivity());
        fakeHeader.setTitle(R.string.pref_header_notifications);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_notification);

        // Add 'data and sync' preferences, and a corresponding header.
        fakeHeader = new PreferenceCategory(this.getActivity());
        fakeHeader.setTitle(R.string.pref_header_data_sync);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_data_sync);
        ListPreference listPreference = (ListPreference) findPreference("sync_frequency");
        listPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d(TAG, "[onPreferenceClick] " + preference.getKey() + " click");
                return true;
            }
        });
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "[onPreferenceChange]" + preference.getKey() + " " + newValue.toString());
                return true;
            }
        });


        PreferenceCategory newHeader = new PreferenceCategory(getActivity());
        newHeader.setTitle("Switch");
        getPreferenceScreen().addPreference(newHeader);
        SwitchPreference switchPreference = new SwitchPreference(getActivity());
        switchPreference.setKey("KEY_SWITCH");
        switchPreference.setTitle("switch button");
        switchPreference.setSummary("switch button summary");
        switchPreference.setDefaultValue(true);
        switchPreference.setSelectable(true);
        switchPreference.setEnabled(true);
        switchPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d(TAG, "[onPreferenceClick] " + preference.getKey() + " click");
                return true;
            }
        });
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "[onPreferenceChange]" + preference.getKey() + " " + newValue.toString());
                return true;
            }
        });
        getPreferenceScreen().addPreference(switchPreference);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
