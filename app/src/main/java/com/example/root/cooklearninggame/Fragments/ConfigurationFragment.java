package com.example.root.cooklearninggame.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.cooklearninggame.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        this.addPreferencesFromResource(R.xml.preferences);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addPreferencesFromResource(R.xml.preferences);
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }*/
}