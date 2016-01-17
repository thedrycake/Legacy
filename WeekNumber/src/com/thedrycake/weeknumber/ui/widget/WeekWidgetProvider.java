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

package com.thedrycake.weeknumber.ui.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.thedrycake.weeknumber.Constants;
import com.thedrycake.weeknumber.R;
import com.thedrycake.weeknumber.Settings;
import com.thedrycake.weeknumber.ui.MainActivity;
import com.thedrycake.weeknumber.util.WeekUtils;

public class WeekWidgetProvider extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		String action = intent.getAction();
		if (Constants.ACTION_WIDGET_SETTINGS_CHANGED.equals(action)) {
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			int[] appWidgetIds = appWidgetManager
					.getAppWidgetIds(new ComponentName(context,
							WeekWidgetProvider.class));
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

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private static void update(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.widget_week);
		Intent intent;
		final int widgetAction = Settings.getWidgetAction(context);
		switch (widgetAction) {
		case 2:
			Uri uri;
			long time = System.currentTimeMillis();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
				builder.appendPath("time");
				builder.appendPath(Long.toString(time));
				uri = builder.build();
			} else {
				uri = Uri.parse("content://com.android.calendar/time/" + time);
			}
			intent = new Intent(Intent.ACTION_VIEW, uri);
			break;
		default:
			intent = new Intent(context, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			break;
		}
		PendingIntent pendingIntent = PendingIntent.getActivity(context,
				appWidgetId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		final int widgetTheme = Settings.getWidgetTheme(context);
		int backgroundDrawableId = 0;
		if (!Settings.isWidgetTransparentBackground(context)) {
			switch (widgetTheme) {
			case 2:
				backgroundDrawableId = R.drawable.appwidget_bg_clickable;
				break;
			default:
				backgroundDrawableId = R.drawable.appwidget_dark_bg_clickable;
				break;
			}
		}
		int weekNumber = WeekUtils.getWeekNumber(
				Settings.getFirstDayOfWeek(context),
				Settings.getMinimalDaysInFirstWeek(context));
		views.setOnClickPendingIntent(R.id.main_container, pendingIntent);
		views.setInt(R.id.main_container, "setBackgroundResource",
				backgroundDrawableId);
		views.setTextViewText(R.id.title, Settings.getWidgetTitle(context));
		views.setTextViewText(R.id.week_number, Integer.toString(weekNumber));
		int textColorId;
		switch (widgetTheme) {
		case 2:
			textColorId = R.color.black;
			break;
		default:
			textColorId = R.color.white;
			break;
		}
		Resources resources = context.getResources();
		views.setTextColor(R.id.title, resources.getColor(textColorId));
		views.setTextColor(R.id.week_number, resources.getColor(textColorId));
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
			int weekTextSizeDimensionId = isKeyguard ? R.dimen.keyguard_widget_title_text_size
					: R.dimen.widget_title_text_size;
			int weekNumberTextSizeDimensionId = isKeyguard ? R.dimen.keyguard_widget_week_number_text_size
					: R.dimen.widget_week_number_text_size;
			Resources resources = context.getResources();
			views.setTextViewTextSize(R.id.title, TypedValue.COMPLEX_UNIT_PX,
					resources.getDimension(weekTextSizeDimensionId));
			views.setTextViewTextSize(R.id.week_number,
					TypedValue.COMPLEX_UNIT_PX,
					resources.getDimension(weekNumberTextSizeDimensionId));
		}
	}

}
