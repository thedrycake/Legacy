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

public interface Dao {

	int delete();

	int delete(String whereClause, String whereArg);

	int delete(String whereClause, String[] whereArgs);

	int deleteById(long id);

	void execSQL(String sql);

	long insert(ContentValues values);

	Cursor query(String[] columns);

	Cursor query(String[] columns, String selection, String selectionArg);

	Cursor query(String[] columns, String selection, String[] selectionArgs);

	Cursor query(String[] columns, String selection, String selectionArg,
			String orderBy);

	Cursor query(String[] columns, String selection, String[] selectionArgs,
			String orderBy);

	Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String orderBy);

	Cursor query(String inTables, Map<String, String> columnMap,
			String[] columns, String selection, String[] selectionArgs,
			String orderBy);

	Cursor queryById(long id, String[] columns);

	Cursor rawQuery(String sql, String selectionArg);

	Cursor rawQuery(String sql, String[] selectionArgs);

	int update(ContentValues values, String whereClause, String whereArg);

	int update(ContentValues values, String whereClause, String[] whereArgs);

	int updateById(long id, ContentValues values);

}