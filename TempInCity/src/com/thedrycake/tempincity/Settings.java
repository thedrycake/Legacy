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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thedrycake.tempincity.city.Cities;
import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity.TemperatureUnit;
import com.thedrycake.tempincity.util.StringUtils;

public abstract class Settings {

	public static final String CHANGE_TEXT_COLOR_WITH_TEMP_KEY = "change_text_color_with_temp";
	public static final String CHANGE_WIDGET_TEXT_COLOR_WITH_TEMP_KEY = "change_widget_text_color_with_temp";
	public static final String CURRENT_CITY_KEY = "current_city";
	public static final String CURRENT_TEMP_UNIT_KEY = "current_temp_unit";
	public static final String TRANSPARENT_WIDGET_BACKGROUND_KEY = "transparent_widget_background";
	public static final String WIDGET_THEME_KEY = "widget_theme";

	private static final String WIDGET_PREFERENCES_NAME = "com.thedrycake.tempincity.widget.TempWidgetProvider";

	public static String getCurrentCityName(Context context) {
		return getCurrentCityName(context, Cities.DEFAULT_CITY.getName());
	}

	public static String getCurrentCityName(Context context, String defaultValue) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(CURRENT_CITY_KEY, defaultValue);
	}

	public static TemperatureUnit getCurrentTempUnit(Context context) {
		String unitName = PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						CURRENT_TEMP_UNIT_KEY, null);
		return TemperatureUnit.fromName(unitName);
	}

	public static String getWidgetCurrentCityName(Context context,
			int appWidgetId) {
		return getWidgetCurrentCityName(context, appWidgetId, null);
	}

	public static String getWidgetCurrentCityName(Context context,
			int appWidgetId, String defaultValue) {
		String key = createKey(Settings.CURRENT_CITY_KEY, appWidgetId);
		return context.getSharedPreferences(WIDGET_PREFERENCES_NAME,
				Context.MODE_PRIVATE).getString(key, defaultValue);
	}

	public static Set<String> getWidgetCurrentCityNames(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				WIDGET_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Set<String> cityNames = new HashSet<String>();
		for (String key : preferences.getAll().keySet()) {
			if (key.startsWith(CURRENT_CITY_KEY)) {
				String cityName = preferences.getString(key, null);
				if (!StringUtils.isNullOrEmpty(cityName)) {
					cityNames.add(cityName);
				}
			}
		}
		return cityNames;
	}

	public static boolean isChangeTextColorWithTemp(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(CHANGE_TEXT_COLOR_WITH_TEMP_KEY, false);
	}

	public static boolean isChangeWidgetTextColorWithTemp(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(CHANGE_WIDGET_TEXT_COLOR_WITH_TEMP_KEY, false);
	}

	public static boolean isTransparentWidgetBackground(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(TRANSPARENT_WIDGET_BACKGROUND_KEY, true);
	}

	public static int getWidgetTheme(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				context).getString(WIDGET_THEME_KEY, Integer.toString(1)));
	}

	public static void removeWidgetSettings(Context context, int appWidgetId) {
		SharedPreferences preferences = context.getSharedPreferences(
				WIDGET_PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor preferencesEditor = preferences.edit();
		String widgetKeyPostfix = createKey(StringUtils.EMPTY, appWidgetId);
		for (String key : preferences.getAll().keySet()) {
			if (key.endsWith(widgetKeyPostfix)) {
				preferencesEditor.remove(key).commit();
			}
		}
	}

	public static void setWidgetCurrentCityName(Context context,
			int appWidgetId, String cityName) {
		String key = createKey(Settings.CURRENT_CITY_KEY, appWidgetId);
		context.getSharedPreferences(WIDGET_PREFERENCES_NAME,
				Context.MODE_PRIVATE).edit().putString(key, cityName).commit();
	}

	private static String createKey(String key, int appWidgetId) {
		return String.format(Locale.US, "%s_%s", key, appWidgetId);
	}

}
