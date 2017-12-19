package com.cpsc310proj.babib.plantam.Settings;

import android.preference.PreferenceActivity;

import com.cpsc310proj.babib.plantam.R;

import java.util.List;

public class PlantamPreferenceActivity extends PreferenceActivity
{
    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return PlantamPreferenceFragment.class.getName().equals(fragmentName);
    }
}
