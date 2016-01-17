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

package com.thedrycake.tempincity.ui.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.thedrycake.tempincity.Constants;
import com.thedrycake.tempincity.R;
import com.thedrycake.tempincity.Settings;
import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity;
import com.thedrycake.tempincity.ui.MainActivity;
import com.thedrycake.tempincity.util.StringUtils;
import com.thedrycake.tempincity.util.TempUtils;

public class TempWidgetProvider extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Settings.removeWidgetSettings(context, appWidgetId);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		String action = intent.getAction();
		if (Constants.ACTION_CURRENT_CITY_CHANGED.equals(action)
				|| Constants.ACTION_TEMP_CHANGED.equals(action)
				|| Constants.ACTION_WIDGET_SETTINGS_CHANGED.equals(action)) {
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			int[] appWidgetIds = appWidgetManager
					.getAppWidgetIds(new ComponentName(context,
							TempWidgetProvider.class));
			onUpdate(context, appWidgetManager, appWidgetIds);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			update(context, appWidgetManager, appWidgetId);
		}
	}

	public static void update(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		update(context, appWidgetManager, appWidgetId,
				Settings.getWidgetCurrentCityName(context, appWidgetId));
	}

	private static void update(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId, String cityName) {
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.widget_temp);
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Constants.EXTRA_CITY_NAME, cityName);
		PendingIntent pendingIntent = PendingIntent.getActivity(context,
				appWidgetId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		final int widgetTheme = Settings.getWidgetTheme(context);
		int backgroundDrawableId = 0;
		if (!Settings.isTransparentWidgetBackground(context)) {
			switch (widgetTheme) {
			case 2:
				backgroundDrawableId = R.drawable.appwidget_bg_clickable;
				break;
			default:
				backgroundDrawableId = R.drawable.appwidget_dark_bg_clickable;
				break;
			}
		}
		if (StringUtils.isNullOrEmpty(cityName)) {
			cityName = Settings.getCurrentCityName(context);
		}
		Double temp = TemperatureEntity.getTemperature(context, cityName);
		CharSequence tempString = context.getText(R.string.not_available);
		int textColorId;
		switch (widgetTheme) {
		case 2:
			textColorId = R.color.black;
			break;
		default:
			textColorId = R.color.white;
			break;
		}
		int cityTextColorId = textColorId;
		int tempTextColorId = textColorId;
		if (temp != null) {
			tempString = TempUtils.convertAndFormat(temp,
					Settings.getCurrentTempUnit(context));
			if (Settings.isChangeWidgetTextColorWithTemp(context)) {
				tempTextColorId = temp > 0 ? R.color.temp_text_warm
						: R.color.temp_text_cold;
			}
		}
		views.setOnClickPendingIntent(R.id.main_container, pendingIntent);
		views.setInt(R.id.main_container, "setBackgroundResource",
				backgroundDrawableId);
		views.setTextViewText(R.id.city, cityName);
		Resources resources = context.getResources();
		views.setTextColor(R.id.city, resources.getColor(cityTextColorId));
		views.setTextViewText(R.id.temp, tempString);
		views.setTextColor(R.id.temp, resources.getColor(tempTextColorId));
		setTextSize(context, appWidgetManager, appWidgetId, views);
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private static void setTextSize(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			RemoteViews views) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
			int category = options.getInt(
					AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, -1);
			boolean isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
			int cityTextSizeDimensionId = isKeyguard ? R.dimen.keyguard_widget_city_text_size
					: R.dimen.widget_city_text_size;
			int tempTextSizeDimensionId = isKeyguard ? R.dimen.keyguard_widget_temp_text_size
					: R.dimen.widget_temp_text_size;
			Resources resources = context.getResources();
			views.setTextViewTextSize(R.id.city, TypedValue.COMPLEX_UNIT_PX,
					resources.getDimension(cityTextSizeDimensionId));
			views.setTextViewTextSize(R.id.temp, TypedValue.COMPLEX_UNIT_PX,
					resources.getDimension(tempTextSizeDimensionId));
		}
	}

}
