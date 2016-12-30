package com.carlos.capstone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 05/01/2016.
 */


public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_activity_settings);
        toolBar.setTitle(getString(R.string.settings_title));
        toolBar.setTitleTextColor(Color.WHITE);

        //http://developer.android.com/intl/es/reference/android/support/v7/appcompat/R.drawable.html
        //by default the color is pale grey
       // final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha); //23.3
        //  final Drawable upArrow= ContextCompat.getDrawable(this,R.drawable.ic_action_back);
        final Drawable upArrow= ContextCompat.getDrawable(this,R.drawable.abc_ic_ab_back_material); //23.2
        //tint the arrow to white
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolBar.setNavigationIcon(upArrow);


        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(SettingsActivity.this);
            }
        });


        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);
        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_animation_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_share_key)));

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_email_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_share_app_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_version_key)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(), LOG_TAG);

    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);
        preference.setOnPreferenceClickListener(this);

        if (preference instanceof CheckBoxPreference) {
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), false));
        } else {
            // Trigger the listener immediately with the preference's
            // current value.
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof CheckBoxPreference) {
            if (preference.getKey() == getString(R.string.pref_animation_key)) {
                String summaryValue = stringValue.equals("true") ? "enabled" : "disabled";
                preference.setSummary(summaryValue);
            }
        } else {
            String key = preference.getKey();
            //we take the versionName and assign it to the pref var.
            if (key == getString(R.string.pref_version_key)) {
                String versionName;
                try {
                    versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

                } catch (PackageManager.NameNotFoundException e) {
                    versionName = null;
                }
                preference.setSummary(versionName);

            } else if (key == getString(R.string.pref_share_app_key)) {

                preference.setSummary(getString(R.string.share_app_string));

            } else if (key == getString(R.string.pref_email_key)) {

                preference.setSummary(getString(R.string.email_string));

            } else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        String key = preference.getKey();
        if (key.equals(getString(R.string.pref_email_key))) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                    getString(R.string.mailto), null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));


        } else if (key.equals(getString(R.string.pref_share_app_key))) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            //https://play.google.com/store/apps/details?id=com.carlos.capstone
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_play_store_address));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return true;
    }
}