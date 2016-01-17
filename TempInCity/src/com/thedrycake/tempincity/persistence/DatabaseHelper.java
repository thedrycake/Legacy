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

package com.thedrycake.tempincity.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity;

public final class DatabaseHelper extends SQLiteOpenHelper {

	public interface Tables {

		String TEMPERATURE = "temperature";

	}

	private static final String DATABASE_NAME = "tempincity.db";
	private static final int DATABASE_VERSION = 4;
	private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

	private static DatabaseHelper sInstance = null;

	public static synchronized DatabaseHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Tables.TEMPERATURE + " ("
				+ TemperatureEntity.Columns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ TemperatureEntity.Columns.CITY_NAME + " TEXT NOT NULL, "
				+ TemperatureEntity.Columns.COUNTRY_CODE + " TEXT NOT NULL, "
				+ TemperatureEntity.Columns.IMPL_TYPE + " TEXT NOT NULL, "
				+ TemperatureEntity.Columns.IMPL_TYPE_ARGS + " TEXT, "
				+ TemperatureEntity.Columns.TEMP + " REAL NOT NULL, "
				+ TemperatureEntity.Columns.TEMP_DATE + " INTEGER NOT NULL, "
				+ TemperatureEntity.Columns.CREATE_DATE + " INTEGER NOT NULL, "
				+ TemperatureEntity.Columns.MODIFICATION_DATE
				+ " INTEGER NOT NULL)");
		db.execSQL(createIndexSql(Tables.TEMPERATURE,
				TemperatureEntity.Columns.CITY_NAME));
		db.execSQL(createIndexSql(Tables.TEMPERATURE,
				TemperatureEntity.Columns.COUNTRY_CODE));
		db.execSQL(createUniqueIndexSql(Tables.TEMPERATURE,
				TemperatureEntity.Columns.CITY_NAME,
				TemperatureEntity.Columns.COUNTRY_CODE));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + "city");
		db.execSQL("DROP TABLE IF EXISTS " + Tables.TEMPERATURE);

		onCreate(db);
	}

	private static String createIndexSql(String tableName,
			String... columnNames) {
		return createIndexSql(false, tableName, columnNames);
	}

	private static String createUniqueIndexSql(String tableName,
			String... columnNames) {
		return createIndexSql(true, tableName, columnNames);
	}

	private static String createIndexSql(boolean unique, String tableName,
			String... columnNames) {
		String indexName = createIndexName(tableName, columnNames);
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE ");
		if (unique) {
			sb.append("UNIQUE ");
		}
		sb.append("INDEX ");
		sb.append("IF NOT EXISTS ");
		sb.append(indexName);
		sb.append(" ON ");
		sb.append(tableName);
		sb.append(" (");
		for (int i = 0; i < columnNames.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(columnNames[i]);
		}
		sb.append(")");
		return sb.toString();
	}

	private static String createIndexName(String tableName,
			String... columnNames) {
		StringBuilder sb = new StringBuilder();
		sb.append(tableName);
		sb.append("_");
		for (String columnName : columnNames) {
			sb.append(columnName);
			sb.append("_");
		}
		sb.append("idx");
		return sb.toString();
	}

}
