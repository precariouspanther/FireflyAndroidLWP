package net.precariouspanther.gdxtest;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;



public class WallpaperSettings extends PreferenceActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}