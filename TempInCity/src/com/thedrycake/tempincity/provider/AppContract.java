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

package com.thedrycake.tempincity.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class AppContract {

	public static final String SCHEME = "content://";
	public static final String AUTHORITY = "com.thedrycake.tempincity.provider";
	public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

	private AppContract() {
	}

	abstract static class AbstractEntity {

		public static final String[] PROJECTION_ID = new String[] { BaseColumns._ID };

	}

	public static final class TemperatureEntity extends AbstractEntity {

		private TemperatureEntity() {
		}

		static final String PATH_ALL = "temperature";
		public static final String PATH_ID = PATH_ALL + "/";
		public static final String PATH_ID_PATTERN = PATH_ID + "#";
		public static final int PATH_ID_POSITION = 1;

		public static final Uri CONTENT_URI = Uri.withAppendedPath(
				AppContract.CONTENT_URI, PATH_ALL);
		public static final Uri CONTENT_ID_URI_BASE = Uri.withAppendedPath(
				AppContract.CONTENT_URI, PATH_ID);
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.withAppendedPath(
				AppContract.CONTENT_URI, PATH_ID_PATTERN);

		static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.thedrycake.tempincity.temperature";
		static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.thedrycake.tempincity.temperature";

		public static final String SORT_ORDER_CITY_NAME = Columns.CITY_NAME
				+ " ASC";

		public static final String[] PROJECTION_TEMP = new String[] { Columns.TEMP };
		public static final String[] PROJECTION_TEMP_AND_TEMP_DATE = new String[] {
				Columns.TEMP, Columns.TEMP_DATE };
		public static final String[] PROJECTION_ALL = new String[] {
				Columns._ID, Columns.CITY_NAME, Columns.COUNTRY_CODE,
				Columns.IMPL_TYPE, Columns.IMPL_TYPE_ARGS, Columns.TEMP,
				Columns.TEMP_DATE, Columns.CREATE_DATE,
				Columns.MODIFICATION_DATE };

		public static enum TemperatureUnit {

			CELSIUS("C"), FAHRENHEIT("F"), KELVIN("K");

			private final String mName;

			private TemperatureUnit(String name) {
				this.mName = name;
			}

			public static TemperatureUnit fromName(String name) {
				if (FAHRENHEIT.getName().equals(name)) {
					return FAHRENHEIT;
				} else if (KELVIN.getName().equals(name)) {
					return KELVIN;
				} else {
					return CELSIUS;
				}
			}

			public String getName() {
				return mName;
			}

			@Override
			public String toString() {
				return getName();
			}

		}

		public static final class Columns implements BaseColumns {

			public static final String CITY_NAME = "city_name";

			public static final String COUNTRY_CODE = "country_code";

			public static final String IMPL_TYPE = "impl_type";

			public static final String IMPL_TYPE_ARGS = "impl_type_args";

			public static final String TEMP = "temp";

			public static final String TEMP_DATE = "temp_date";

			public static final String CREATE_DATE = "created";

			public static final String MODIFICATION_DATE = "modified";

		}

		public static Double getTemperature(Context context, String cityName) {
			return getTemperature(context.getContentResolver(), cityName);
		}

		public static Double getTemperature(ContentResolver cr, String cityName) {
			Cursor cursor = null;
			try {
				cursor = cr.query(TemperatureEntity.CONTENT_URI,
						TemperatureEntity.PROJECTION_TEMP,
						TemperatureEntity.Columns.CITY_NAME + " = ?",
						new String[] { cityName }, null);
				if (cursor.moveToNext()) {
					return cursor.getDouble(0);
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
			return null;
		}

	}

}
