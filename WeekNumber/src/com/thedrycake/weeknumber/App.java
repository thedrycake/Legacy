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

package com.thedrycake.weeknumber;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class App extends Application {

	private static App sInstance;

	private static final OnSharedPreferenceChangeListener mPreferenceChangeListener = new OnSharedPreferenceChangeListener() {

		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (Settings.KEY_FIRST_DAY_OF_WEEK.equals(key)
					|| Settings.KEY_MINIMAL_DAYS_IN_FIRST_WEEK.equals(key)
					|| Settings.KEY_WIDGET_ACTION.equals(key)
					|| Settings.KEY_WIDGET_THEME.equals(key)
					|| Settings.KEY_WIDGET_TITLE.equals(key)
					|| Settings.KEY_WIDGET_TRANSPARENT_BACKGROUND.equals(key)) {
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
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		PreferenceManager.getDefaultSharedPreferences(sInstance)
				.registerOnSharedPreferenceChangeListener(
						mPreferenceChangeListener);
	}

}
