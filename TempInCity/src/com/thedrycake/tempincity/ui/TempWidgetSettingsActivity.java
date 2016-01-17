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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.thedrycake.tempincity.R;
import com.thedrycake.tempincity.Settings;
import com.thedrycake.tempincity.city.Cities;
import com.thedrycake.tempincity.service.TemperatureService;
import com.thedrycake.tempincity.ui.widget.TempWidgetProvider;
import com.thedrycake.tempincity.util.StringUtils;

public class TempWidgetSettingsActivity extends ListActivity {

	private CityAdapter mAdapter;
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
		List<String> cityNameList = new ArrayList<String>(Cities.getCityNames());
		Collections.sort(cityNameList);
		cityNameList.add(0, String.format(Locale.US, "< %s >",
				getString(R.string.app_current_city)));
		mAdapter = new CityAdapter(this, android.R.layout.simple_list_item_1,
				cityNameList);
		setListAdapter(mAdapter);
		setFastScrollAlwaysVisible();
		getListView().setFastScrollEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_temp_widget_configure, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String cityName = null;
		if (position > 0) {
			cityName = mAdapter.getItem(position);
		}
		Settings.setWidgetCurrentCityName(this, mAppWidgetId, cityName);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		TempWidgetProvider.update(this, appWidgetManager, mAppWidgetId);
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);
		TemperatureService.startService(this);
		finish();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setFastScrollAlwaysVisible() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getListView().setFastScrollAlwaysVisible(true);
		}
	}

	private static class CityAdapter extends ArrayAdapter<String> implements
			SectionIndexer {

		private HashMap<String, Integer> mAlphaIndexer;
		private String[] mSections;

		@SuppressLint("DefaultLocale")
		public CityAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			mAlphaIndexer = new HashMap<String, Integer>();
			int size = objects.size();
			for (int i = size - 1; i >= 0; i--) {
				String item = (String) objects.get(i);
				String letter = item.substring(0, 1);
				if (!StringUtils.isNullOrEmpty(letter)) {
					mAlphaIndexer.put(letter.toUpperCase(Locale.US), i);
				}
			}
			ArrayList<String> sectionList = new ArrayList<String>(
					mAlphaIndexer.keySet());
			Collections.sort(sectionList);
			mSections = new String[sectionList.size()];
			sectionList.toArray(mSections);
		}

		public int getPositionForSection(int section) {
			return mAlphaIndexer.get(mSections[section]);
		}

		public int getSectionForPosition(int position) {
			return 0;
		}

		public Object[] getSections() {
			return mSections;
		}

	}

}
