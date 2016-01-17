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

package com.thedrycake.tempincity;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.thedrycake.tempincity.city.Cities;
import com.thedrycake.tempincity.service.TemperatureService;
import com.thedrycake.tempincity.util.StringUtils;

public class App extends Application {

	private static App sInstance;

	private static final OnSharedPreferenceChangeListener sPreferenceChangeListener = new OnSharedPreferenceChangeListener() {

		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (Settings.CURRENT_CITY_KEY.equals(key)) {
				Broadcasts.sendCurrentCityChanged(sInstance);
				TemperatureService.startService(sInstance);
			} else if (Settings.CURRENT_TEMP_UNIT_KEY.equals(key)) {
				Broadcasts.sendTempChanged(sInstance);
			} else if (Settings.CHANGE_WIDGET_TEXT_COLOR_WITH_TEMP_KEY
					.equals(key)
					|| Settings.TRANSPARENT_WIDGET_BACKGROUND_KEY.equals(key)
					|| Settings.WIDGET_THEME_KEY.equals(key)) {
				Broadcasts.sendWidgetSettingsChanged(sInstance);
			}
		}

	};

	public static App getInstance() {
		return sInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		setCurrentCityDefaultValue();
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		PreferenceManager.getDefaultSharedPreferences(this)
				.registerOnSharedPreferenceChangeListener(
						sPreferenceChangeListener);
		TemperatureService.startService(this);
	}

	private void setCurrentCityDefaultValue() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String cityName = preferences
				.getString(Settings.CURRENT_CITY_KEY, null);
		if (StringUtils.isNullOrEmpty(cityName)) {
			preferences
					.edit()
					.putString(Settings.CURRENT_CITY_KEY,
							Cities.DEFAULT_CITY.getName()).commit();
		}
	}

}
