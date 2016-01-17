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

import android.content.ContentValues;

/**
 * Common content utility methods.
 */
public abstract class ContentUtils {

	public static Boolean getAsBoolean(ContentValues values, String key,
			Boolean defaultValue) {
		Boolean value = values != null ? values.getAsBoolean(key) : null;
		return value != null ? value : defaultValue;
	}

	public static Double getAsDouble(ContentValues values, String key,
			Double defaultValue) {
		Double value = values != null ? values.getAsDouble(key) : null;
		return value != null ? value : defaultValue;
	}

	public static <T extends Enum<T>> T getAsEnum(ContentValues values,
			String key, Class<T> enumType, T defaultValue) {
		return EnumUtils.valueOf(enumType, getAsString(values, key, null),
				defaultValue);
	}

	public static Integer getAsInteger(ContentValues values, String key,
			Integer defaultValue) {
		Integer value = values != null ? values.getAsInteger(key) : null;
		return value != null ? value : defaultValue;
	}

	public static Long getAsLong(ContentValues values, String key,
			Long defaultValue) {
		Long value = values != null ? values.getAsLong(key) : null;
		return value != null ? value : defaultValue;
	}

	public static String getAsString(ContentValues values, String key,
			String defaultValue) {
		String value = values != null ? values.getAsString(key) : null;
		return value != null ? value : defaultValue;
	}

	public static void putDefaultIfNotPresent(ContentValues values, String key,
			Boolean defaultValue) {
		if (!values.containsKey(key) || values.getAsBoolean(key) == null) {
			values.put(key, defaultValue);
		}
	}

	public static void putDefaultIfNotPresent(ContentValues values, String key,
			Double defaultValue) {
		if (!values.containsKey(key) || values.getAsDouble(key) == null) {
			values.put(key, defaultValue);
		}
	}

	public static void putDefaultIfNotPresent(ContentValues values, String key,
			Integer defaultValue) {
		if (!values.containsKey(key) || values.getAsInteger(key) == null) {
			values.put(key, defaultValue);
		}
	}

	public static void putDefaultIfNotPresent(ContentValues values, String key,
			Long defaultValue) {
		if (!values.containsKey(key) || values.getAsLong(key) == null) {
			values.put(key, defaultValue);
		}
	}

	public static void putDefaultIfNotPresent(ContentValues values, String key,
			String defaultValue) {
		if (!values.containsKey(key) || values.getAsString(key) == null) {
			values.put(key, defaultValue);
		}
	}

	public static void putEmptyStringIfNotPresent(ContentValues values,
			String key) {
		putDefaultIfNotPresent(values, key, StringUtils.EMPTY);
	}

	public static void putValueOrDefault(ContentValues values, String key,
			Boolean value, Boolean defaultValue) {
		values.put(key, value);
		putDefaultIfNotPresent(values, key, defaultValue);
	}

	public static void putValueOrDefault(ContentValues values, String key,
			Double value, Double defaultValue) {
		values.put(key, value);
		putDefaultIfNotPresent(values, key, defaultValue);
	}

	public static void putValueOrDefault(ContentValues values, String key,
			Integer value, Integer defaultValue) {
		values.put(key, value);
		putDefaultIfNotPresent(values, key, defaultValue);
	}

	public static void putValueOrDefault(ContentValues values, String key,
			Long value, Long defaultValue) {
		values.put(key, value);
		putDefaultIfNotPresent(values, key, defaultValue);
	}

	public static void putValueOrDefault(ContentValues values, String key,
			String value, String defaultValue) {
		values.put(key, value);
		putDefaultIfNotPresent(values, key, defaultValue);
	}

	public static void putValueOrEmptyString(ContentValues values, String key,
			String value) {
		values.put(key, value);
		putEmptyStringIfNotPresent(values, key);
	}

}
