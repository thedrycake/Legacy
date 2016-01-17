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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.thedrycake.tempincity.Constants;
import com.thedrycake.tempincity.R;
import com.thedrycake.tempincity.Settings;
import com.thedrycake.tempincity.city.Cities;
import com.thedrycake.tempincity.city.City;
import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity;
import com.thedrycake.tempincity.service.TemperatureService;
import com.thedrycake.tempincity.util.StringUtils;
import com.thedrycake.tempincity.util.TempUtils;

public class MainActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	private TextView mTempTextView;
	private TextView mTempDateTextView;

	private String mIntentCityName;
	private final SimpleDateFormat mTempDateFormat;

	public MainActivity() {
		mTempDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setHomeButtonEnabled();
		mTempTextView = (TextView) findViewById(R.id.temp);
		mTempTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onTempClick();
			}

		});
		mTempDateTextView = (TextView) findViewById(R.id.temp_date);
		Intent intent = getIntent();
		Bundle extras = intent != null ? intent.getExtras() : null;
		mIntentCityName = extras != null ? extras
				.getString(Constants.EXTRA_CITY_NAME) : null;
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setTitle();
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_update:
			TemperatureService.startService(this);
			return true;
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(this,
				TemperatureEntity.CONTENT_URI,
				TemperatureEntity.PROJECTION_TEMP_AND_TEMP_DATE,
				TemperatureEntity.Columns.CITY_NAME + " = ?",
				new String[] { getCurrentCityName() }, null);
		return cursorLoader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		setTemp(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		setTemp(null);
	}

	private String getCurrentCityName() {
		return !StringUtils.isNullOrEmpty(mIntentCityName) ? mIntentCityName
				: Settings.getCurrentCityName(this);
	}

	private void onTempClick() {
		City city = Cities.getCityByName(getCurrentCityName(), null);
		URL webAddress = null;
		try {
			webAddress = city != null ? city.getUserFriendlyWebAddress() : null;
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Incorrect user friendly web address", e);
		}
		if (webAddress != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webAddress
					.toExternalForm()));
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void setHomeButtonEnabled() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setHomeButtonEnabled(false);
		}
	}

	private void setTitle() {
		setTitle(getCurrentCityName());
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setTitle(String cityName) {
		CharSequence title;
		if (!StringUtils.isNullOrEmpty(cityName)) {
			title = getText(R.string.temp_in) + " " + cityName;
		} else {
			title = getText(R.string.app_name);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			final ActionBar actionBar = getActionBar();
			actionBar.setTitle(title);
		} else {
			setTitle(title);
		}
	}

	private void setTemp(Cursor cursor) {
		if (cursor != null && cursor.moveToFirst()) {
			double temp = cursor.getDouble(cursor
					.getColumnIndex(TemperatureEntity.Columns.TEMP));
			long tempDateInMillis = cursor.getLong(cursor
					.getColumnIndex(TemperatureEntity.Columns.TEMP_DATE));
			setTemp(temp);
			setTempDate(tempDateInMillis);
		} else {
			setTempNotAvailable();
			setTempDateNotAvailable();
		}
	}

	private void setTemp(double temp) {
		mTempTextView.setText(TempUtils.convertAndFormat(temp,
				Settings.getCurrentTempUnit(this)));
		int textColorId = R.color.black;
		if (Settings.isChangeTextColorWithTemp(this)) {
			textColorId = temp > 0 ? R.color.temp_text_warm
					: R.color.temp_text_cold;
		}
		mTempTextView.setTextColor(getResources().getColor(textColorId));
	}

	private void setTempNotAvailable() {
		mTempTextView.setText(R.string.not_available);
		mTempTextView.setTextColor(getResources().getColor(R.color.black));
	}

	private void setTempDate(long tempDateInMillis) {
		setTempDate(new Date(tempDateInMillis));
	}

	private void setTempDate(Date tempDate) {
		mTempDateTextView.setText(getText(R.string.updated_at) + " "
				+ mTempDateFormat.format(tempDate));
	}

	private void setTempDateNotAvailable() {
		mTempDateTextView.setText(getText(R.string.updated_at) + " "
				+ getText(R.string.not_available));
	}

}
