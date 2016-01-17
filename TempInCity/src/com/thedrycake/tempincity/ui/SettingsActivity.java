/* 
 * Copyright (C) 2013 The Drycake Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thedrycake.tempincity.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.thedrycake.tempincity.R;
import com.thedrycake.tempincity.Settings;
import com.thedrycake.tempincity.city.Cities;
import com.thedrycake.tempincity.util.StringUtils;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDisplayHomeAsUpEnabled();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		final ListPreference cityPreference = (ListPreference) findPreference(Settings.CURRENT_CITY_KEY);
		setCityPreferenceData(cityPreference);
		bindPreferenceSummaryToValue(cityPreference);
		bindPreferenceSummaryToValue(findPreference(Settings.CURRENT_TEMP_UNIT_KEY));
		bindPreferenceSummaryToValue(findPreference(Settings.WIDGET_THEME_KEY));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent parentActivityIntent = new Intent(this, MainActivity.class);
			parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(parentActivityIntent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void setDisplayHomeAsUpEnabled() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	private static void setCityPreferenceData(ListPreference cityPreference) {
		List<String> cityNameList = new ArrayList<String>(Cities.getCityNames());
		Collections.sort(cityNameList);
		String[] cityNameArray = cityNameList.toArray(new String[cityNameList
				.size()]);
		cityPreference.setEntries(cityNameArray);
		cityPreference.setEntryValues(cityNameArray);
	}

	private static void bindPreferenceSummaryToValue(Preference preference) {
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						StringUtils.EMPTY));
	}

	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {

		public boolean onPreferenceChange(Preference preference, Object newValue) {
			String stringValue = newValue.toString();
			if (preference instanceof ListPreference) {
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);
			} else {
				preference.setSummary(stringValue);
			}
			return true;
		}

	};

}
