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

package com.thedrycake.weeknumber.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.thedrycake.weeknumber.R;
import com.thedrycake.weeknumber.Settings;
import com.thedrycake.weeknumber.util.WeekUtils;

public class MainActivity extends Activity {

	private TextView mWeekNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setHomeButtonEnabled();
		mWeekNumber = (TextView) findViewById(R.id.week_number);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setWeekNumber();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void setHomeButtonEnabled() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setHomeButtonEnabled(false);
		}
	}

	private void setWeekNumber() {
		int weekNumber = WeekUtils.getWeekNumber(
				Settings.getFirstDayOfWeek(this),
				Settings.getMinimalDaysInFirstWeek(this));
		setWeekNumber(weekNumber);
	}

	private void setWeekNumber(int weekNumber) {
		mWeekNumber.setText(Integer.toString(weekNumber));
	}

}
