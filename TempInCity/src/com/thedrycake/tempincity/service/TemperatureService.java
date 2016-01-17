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

package com.thedrycake.tempincity.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.thedrycake.tempincity.Broadcasts;
import com.thedrycake.tempincity.Constants;
import com.thedrycake.tempincity.Settings;
import com.thedrycake.tempincity.city.Cities;
import com.thedrycake.tempincity.city.City;
import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity;
import com.thedrycake.tempincity.util.StringUtils;

public class TemperatureService extends IntentService {

	public static void startService(Context context) {
		if (context != null) {
			Intent intent = new Intent(context, TemperatureService.class);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0,
					intent, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarm = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			alarm.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
					Constants.TEN_MINUTES, pendingIntent);
		}
	}

	public TemperatureService() {
		super(TemperatureService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Set<String> cityNamesToRefresh = new HashSet<String>();
		cityNamesToRefresh.addAll(Settings.getWidgetCurrentCityNames(this));
		cityNamesToRefresh.add(Settings.getCurrentCityName(this));
		for (String cityName : cityNamesToRefresh) {
			if (!StringUtils.isNullOrEmpty(cityName)) {
				City city = Cities.getCityByName(cityName, null);
				if (city != null) {
					refreshCity(city);
				}
			}
		}
		if (cityNamesToRefresh.size() > 0) {
			Broadcasts.sendTempChanged(this);
		}
	}

	private void refreshCity(City city) {
		if (city == null) {
			return;
		}
		city.refresh();
		Double temperature = city.getTemperatureInCelsius();
		if (temperature != null) {
			String cityName = city.getName();
			Date temperatureDate = city.getTemperatureDate();
			ContentValues values = new ContentValues();
			values.put(TemperatureEntity.Columns.CITY_NAME, cityName);
			values.put(TemperatureEntity.Columns.TEMP, temperature);
			if (temperatureDate != null) {
				values.put(TemperatureEntity.Columns.TEMP_DATE,
						temperatureDate.getTime());
			}
			Cursor cursor = null;
			try {
				cursor = getContentResolver().query(
						TemperatureEntity.CONTENT_URI,
						TemperatureEntity.PROJECTION_ID,
						TemperatureEntity.Columns.CITY_NAME + " = ?",
						new String[] { cityName }, null);
				if (cursor.moveToNext()) {
					Uri uri = ContentUris.withAppendedId(
							TemperatureEntity.CONTENT_URI, cursor.getLong(0));
					getContentResolver().update(uri, values, null, null);
				} else {
					getContentResolver().insert(TemperatureEntity.CONTENT_URI,
							values);
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
	}

}
