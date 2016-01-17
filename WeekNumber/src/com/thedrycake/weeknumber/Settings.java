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

import java.util.Calendar;

import android.content.Context;
import android.preference.PreferenceManager;

import com.thedrycake.weeknumber.util.StringUtils;

public abstract class Settings {

	public static final String KEY_FIRST_DAY_OF_WEEK = "first_day_of_week";
	public static final String KEY_MINIMAL_DAYS_IN_FIRST_WEEK = "minimal_days_in_first_week";
	public static final String KEY_WIDGET_ACTION = "widget_action";
	public static final String KEY_WIDGET_THEME = "widget_theme";
	public static final String KEY_WIDGET_TITLE = "widget_title";
	public static final String KEY_WIDGET_TRANSPARENT_BACKGROUND = "widget_transparent_background";

	public static int getFirstDayOfWeek(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				context).getString(KEY_FIRST_DAY_OF_WEEK,
				Integer.toString(Calendar.MONDAY)));
	}

	public static int getMinimalDaysInFirstWeek(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				context).getString(KEY_MINIMAL_DAYS_IN_FIRST_WEEK,
				Integer.toString(4)));
	}

	public static int getWidgetAction(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				context).getString(KEY_WIDGET_ACTION, Integer.toString(1)));
	}

	public static int getWidgetTheme(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				context).getString(KEY_WIDGET_THEME, Integer.toString(1)));
	}

	public static String getWidgetTitle(Context context) {
		String weekText = PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						KEY_WIDGET_TITLE, null);
		if (weekText != null) {
			weekText = weekText.trim();
		}
		if (StringUtils.isNullOrEmpty(weekText)) {
			weekText = context.getString(R.string.week);
		}
		return weekText;
	}

	public static boolean isWidgetTransparentBackground(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(KEY_WIDGET_TRANSPARENT_BACKGROUND, true);
	}

}
