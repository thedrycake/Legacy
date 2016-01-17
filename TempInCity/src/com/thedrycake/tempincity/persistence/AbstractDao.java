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

import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import com.thedrycake.tempincity.util.ContentUtils;
import com.thedrycake.tempincity.util.Preconditions;

abstract class AbstractDao implements Dao {

	private final SQLiteOpenHelper mDatabaseHelper;

	public AbstractDao(SQLiteOpenHelper databaseHelper) {
		Preconditions.checkNotNull(databaseHelper, "databaseHelper");
		mDatabaseHelper = databaseHelper;
	}

	public int delete() {
		return delete(null, (String[]) null);
	}

	public int delete(String whereClause, String whereArg) {
		return delete(whereClause, new String[] { whereArg });
	}

	public int delete(String whereClause, String[] whereArgs) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count = db.delete(getTable(), whereClause, whereArgs);
		return count;
	}

	public int deleteById(long id) {
		return delete(BaseColumns._ID + " = ?", Long.toString(id));
	}

	public void execSQL(String sql) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		db.execSQL(sql);
	}

	public long insert(ContentValues values) {
		if (values == null) {
			values = new ContentValues();
		}
		setDefaultValuesForInsert(values);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		long id = db.insert(getTable(), null, values);
		if (id == -1) {
			throw new SQLException("Failed to insert.");
		}
		return id;
	}

	public Cursor query(String[] columns) {
		return query(columns, null, (String[]) null);
	}

	public Cursor query(String[] columns, String selection, String selectionArg) {
		return query(columns, selection, new String[] { selectionArg });
	}

	public Cursor query(String[] columns, String selection,
			String[] selectionArgs) {
		return query(columns, selection, selectionArgs, null);
	}

	public Cursor query(String[] columns, String selection,
			String selectionArg, String orderBy) {
		return query(columns, selection, new String[] { selectionArg }, orderBy);
	}

	public Cursor query(String[] columns, String selection,
			String[] selectionArgs, String orderBy) {
		return query(getTable(), columns, selection, selectionArgs, orderBy);
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String orderBy) {
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		return db.query(false, table, columns, selection, selectionArgs, null,
				null, orderBy, null);
	}

	public Cursor query(String inTables, Map<String, String> columnMap,
			String[] columns, String selection, String[] selectionArgs,
			String orderBy) {
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(inTables);
		queryBuilder.setProjectionMap(columnMap);
		return queryBuilder.query(db, columns, selection, selectionArgs, null,
				null, orderBy, null);
	}

	public Cursor queryById(long id, String[] columns) {
		return query(columns, BaseColumns._ID + " = ?", Long.toString(id));
	}

	public Cursor rawQuery(String sql, String selectionArg) {
		return rawQuery(sql, new String[] { selectionArg });
	}

	public Cursor rawQuery(String sql, String[] selectionArgs) {
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		return db.rawQuery(sql, selectionArgs);
	}

	public int update(ContentValues values, String whereClause, String whereArg) {
		return update(values, whereClause, new String[] { whereArg });
	}

	public int update(ContentValues values, String whereClause,
			String[] whereArgs) {
		if (values == null) {
			values = new ContentValues();
		}
		setDefaultValuesForUpdate(values);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count = db.update(getTable(), values, whereClause, whereArgs);
		return count;
	}

	public int updateById(long id, ContentValues values) {
		return update(values, BaseColumns._ID + " = ?", Long.toString(id));
	}

	protected abstract String getTable();

	protected void setDefaultValuesForInsert(ContentValues values) {
	}

	protected void setDefaultValuesForUpdate(ContentValues values) {
	}

	protected void putDefaultIfNotPresent(ContentValues values, String key,
			Boolean defaultValue) {
		ContentUtils.putDefaultIfNotPresent(values, key, defaultValue);
	}

	protected void putDefaultIfNotPresent(ContentValues values, String key,
			Double defaultValue) {
		ContentUtils.putDefaultIfNotPresent(values, key, defaultValue);
	}

	protected void putDefaultIfNotPresent(ContentValues values, String key,
			Integer defaultValue) {
		ContentUtils.putDefaultIfNotPresent(values, key, defaultValue);
	}

	protected void putDefaultIfNotPresent(ContentValues values, String key,
			Long defaultValue) {
		ContentUtils.putDefaultIfNotPresent(values, key, defaultValue);
	}

	protected void putDefaultIfNotPresent(ContentValues values, String key,
			String defaultValue) {
		ContentUtils.putDefaultIfNotPresent(values, key, defaultValue);
	}

	protected void putEmptyStringIfNotPresent(ContentValues values, String key) {
		ContentUtils.putEmptyStringIfNotPresent(values, key);
	}

}
