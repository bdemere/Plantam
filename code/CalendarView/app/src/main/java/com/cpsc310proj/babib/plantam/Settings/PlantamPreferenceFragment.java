package com.cpsc310proj.babib.plantam.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.cpsc310proj.babib.plantam.R;

public class PlantamPreferenceFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.plantam_fragment_preference);
    }
}