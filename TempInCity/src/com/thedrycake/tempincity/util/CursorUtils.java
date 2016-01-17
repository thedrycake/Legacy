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

package com.thedrycake.tempincity.util;

import android.database.Cursor;

/**
 * Common cursor utility methods.
 */
public abstract class CursorUtils {

	public static void dumpCurrentColumn(Cursor cursor, String columnName,
			StringBuilder sb) {
		if (cursor == null || sb == null) {
			return;
		}
		int columnIndex = cursor.getColumnIndex(columnName);
		if (columnIndex != -1) {
			sb.append(columnName);
			sb.append("=");
			if (cursor.isNull(columnIndex)) {
				sb.append("null");
			} else {
				try {
					sb.append(cursor.getString(columnIndex));
				} catch (Exception e) {
					sb.append("unprintable");
				}
			}
		}
	}

	public static String dumpCurrentColumnToString(Cursor cursor,
			String columnName) {
		StringBuilder sb = new StringBuilder();
		dumpCurrentColumn(cursor, columnName, sb);
		return sb.toString();
	}

	public static <T extends Enum<T>> T getEnum(Cursor cursor,
			String columnName, Class<T> enumType, T defaultValue) {
		return EnumUtils.valueOf(enumType, getString(cursor, columnName, null),
				defaultValue);
	}

	public static Double getDouble(Cursor cursor, String columnName,
			Double defaultValue) {
		int columnIndex = cursor != null ? cursor.getColumnIndex(columnName)
				: -1;
		if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
			return cursor.getDouble(columnIndex);
		} else {
			return defaultValue;
		}
	}

	public static Integer getInteger(Cursor cursor, String columnName,
			Integer defaultValue) {
		int columnIndex = cursor != null ? cursor.getColumnIndex(columnName)
				: -1;
		if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
			return cursor.getInt(columnIndex);
		} else {
			return defaultValue;
		}
	}

	public static Long getLong(Cursor cursor, String columnName,
			Long defaultValue) {
		int columnIndex = cursor != null ? cursor.getColumnIndex(columnName)
				: -1;
		if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
			return cursor.getLong(columnIndex);
		} else {
			return defaultValue;
		}
	}

	public static String getString(Cursor cursor, String columnName,
			String defaultValue) {
		int columnIndex = cursor != null ? cursor.getColumnIndex(columnName)
				: -1;
		if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
			return cursor.getString(columnIndex);
		} else {
			return defaultValue;
		}
	}

}
