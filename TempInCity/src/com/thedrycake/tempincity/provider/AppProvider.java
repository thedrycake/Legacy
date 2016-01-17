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

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.thedrycake.tempincity.persistence.Dao;
import com.thedrycake.tempincity.persistence.TemperatureDao;
import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity;

public class AppProvider extends ContentProvider {

	private static final String AUTHORITY = AppContract.AUTHORITY;

	private static final UriMatcher sUriMatcher;

	private static final int MATCH_TEMPERATURES = 3;
	private static final int MATCH_TEMPERATURE_ID = 4;

	private Dao temperatureDao;

	static {

		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		sUriMatcher.addURI(AUTHORITY, TemperatureEntity.PATH_ALL,
				MATCH_TEMPERATURES);
		sUriMatcher.addURI(AUTHORITY, TemperatureEntity.PATH_ID_PATTERN,
				MATCH_TEMPERATURE_ID);

	}

	@Override
	public boolean onCreate() {
		temperatureDao = new TemperatureDao(getContext());
		return true;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {

		int count = 0;
		long id;

		switch (sUriMatcher.match(uri)) {

		case MATCH_TEMPERATURES:
			count = temperatureDao.delete(where, whereArgs);
			break;
		case MATCH_TEMPERATURE_ID:
			id = Long.parseLong(uri.getPathSegments().get(
					TemperatureEntity.PATH_ID_POSITION));
			count = temperatureDao.deleteById(id);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		getContext().getContentResolver().notifyChange(uri, null);

		return count;
	}

	@Override
	public String getType(Uri uri) {

		switch (sUriMatcher.match(uri)) {

		case MATCH_TEMPERATURES:
			return TemperatureEntity.CONTENT_TYPE;
		case MATCH_TEMPERATURE_ID:
			return TemperatureEntity.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);

		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {

		Uri insertedUri = null;
		long id;

		switch (sUriMatcher.match(uri)) {

		case MATCH_TEMPERATURES:
			id = temperatureDao.insert(initialValues);
			if (id != -1) {
				insertedUri = ContentUris.withAppendedId(
						TemperatureEntity.CONTENT_ID_URI_BASE, id);
			}
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);

		}

		if (insertedUri != null) {
			getContext().getContentResolver().notifyChange(insertedUri, null);
			return insertedUri;
		}

		throw new SQLException("Failed to insert row into URI: " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor cursor = null;
		long id;

		switch (sUriMatcher.match(uri)) {

		case MATCH_TEMPERATURES:
			cursor = temperatureDao.query(projection, selection, selectionArgs,
					sortOrder);
			break;
		case MATCH_TEMPERATURE_ID:
			id = Long.parseLong(uri.getPathSegments().get(
					TemperatureEntity.PATH_ID_POSITION));
			cursor = temperatureDao.queryById(id, projection);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);

		}

		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues updatedValues, String where,
			String[] whereArgs) {

		int count = 0;
		long id;

		switch (sUriMatcher.match(uri)) {

		case MATCH_TEMPERATURES:
			count = temperatureDao.update(updatedValues, where, whereArgs);
			break;
		case MATCH_TEMPERATURE_ID:
			id = Long.parseLong(uri.getPathSegments().get(
					TemperatureEntity.PATH_ID_POSITION));
			count = temperatureDao.updateById(id, updatedValues);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);

		}

		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return count;
	}

}
