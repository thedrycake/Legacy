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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Common JSON utility methods.
 */
public abstract class JsonUtils {

	/**
	 * Create a new {@link org.json.JSONArray}.
	 * 
	 * @return the JSON Array.
	 */
	public static JSONArray createJsonArray() {
		return new JSONArray();
	}

	/**
	 * Create a new {@link org.json.JSONArray} from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @return the JSON Array.
	 * @throws JSONException
	 *             if the parse fails.
	 */
	public static JSONArray createJsonArray(String json) throws JSONException {
		return StringUtils.isNullOrEmpty(json) ? new JSONArray()
				: new JSONArray(json);
	}

	/**
	 * Create a new {@link org.json.JSONArray} from a JSON String without
	 * throwing any exceptions.
	 * 
	 * @param json
	 *            the JSON String.
	 * @return the JSON Array if the parse succeed, otherwise {@code null}.
	 */
	public static JSONArray createJsonArrayQuietly(String json) {
		try {
			return createJsonArray(json);
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Create a new {@link org.json.JSONObject}.
	 * 
	 * @return the JSON Object.
	 */
	public static JSONObject createJsonObject() {
		return new JSONObject();
	}

	/**
	 * Create a new {@link org.json.JSONObject} from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @return the JSON Object.
	 * @throws JSONException
	 *             if the parse fails.
	 */
	public static JSONObject createJsonObject(String json) throws JSONException {
		return StringUtils.isNullOrEmpty(json) ? new JSONObject()
				: new JSONObject(json);
	}

	/**
	 * Create a new {@link org.json.JSONObject} from a JSON String without
	 * throwing any exceptions.
	 * 
	 * @param json
	 *            the JSON String.
	 * @return the JSON Object if the parse succeed, otherwise {@code null}.
	 */
	public static JSONObject createJsonObjectQuietly(String json) {
		try {
			return createJsonObject(json);
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Retrieve a Boolean value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @return the Boolean value if it exists, otherwise {@code null}.
	 */
	public static Boolean getBoolean(String json, String name) {
		return getBoolean(json, name, null);
	}

	/**
	 * Retrieve a Boolean value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Boolean value if it exists, otherwise the default value.
	 */
	public static Boolean getBoolean(String json, String name,
			Boolean defaultValue) {
		String value = getString(json, name);
		if (!StringUtils.isNullOrEmpty(value)) {
			return Boolean.parseBoolean(value);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Boolean value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @return the Boolean value if it exists, otherwise {@code null}.
	 */
	public static Boolean getBoolean(JSONObject jsonObject, String name) {
		return getBoolean(jsonObject, name, null);
	}

	/**
	 * Retrieve a Boolean value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Boolean value if it exists, otherwise the default value.
	 */
	public static Boolean getBoolean(JSONObject jsonObject, String name,
			Boolean defaultValue) {
		String stringValue = getString(jsonObject, name);
		if (!StringUtils.isNullOrEmpty(stringValue)) {
			return Boolean.parseBoolean(stringValue);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Double value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @return the Double value if it exists, otherwise {@code null}.
	 */
	public static Double getDouble(String json, String name) {
		return getDouble(json, name, null);
	}

	/**
	 * Retrieve a Double value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Double value if it exists, otherwise the default value.
	 */
	public static Double getDouble(String json, String name, Double defaultValue) {
		String value = getString(json, name);
		if (!StringUtils.isNullOrEmpty(value)) {
			return Double.parseDouble(value);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Double value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @return the Double value if it exists, otherwise {@code null}.
	 */
	public static Double getDouble(JSONObject jsonObject, String name) {
		return getDouble(jsonObject, name, null);
	}

	/**
	 * Retrieve a Double value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Double value if it exists, otherwise the default value.
	 */
	public static Double getDouble(JSONObject jsonObject, String name,
			Double defaultValue) {
		String stringValue = getString(jsonObject, name);
		if (!StringUtils.isNullOrEmpty(stringValue)) {
			return Double.parseDouble(stringValue);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Integer value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @return the Integer value if it exists, otherwise {@code null}.
	 */
	public static Integer getInteger(String json, String name) {
		return getInteger(json, name, null);
	}

	/**
	 * Retrieve a Integer value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Integer value if it exists, otherwise the default value.
	 */
	public static Integer getInteger(String json, String name,
			Integer defaultValue) {
		String value = getString(json, name);
		if (!StringUtils.isNullOrEmpty(value)) {
			return Integer.parseInt(value);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Integer value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @return the Integer value if it exists, otherwise {@code null}.
	 */
	public static Integer getInteger(JSONObject jsonObject, String name) {
		return getInteger(jsonObject, name, null);
	}

	/**
	 * Retrieve a Integer value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Integer value if it exists, otherwise the default value.
	 */
	public static Integer getInteger(JSONObject jsonObject, String name,
			Integer defaultValue) {
		String stringValue = getString(jsonObject, name);
		if (!StringUtils.isNullOrEmpty(stringValue)) {
			return Integer.parseInt(stringValue);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Long value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @return the Long value if it exists, otherwise {@code null}.
	 */
	public static Long getLong(String json, String name) {
		return getLong(json, name, null);
	}

	/**
	 * Retrieve a Long value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Long value if it exists, otherwise the default value.
	 */
	public static Long getLong(String json, String name, Long defaultValue) {
		String value = getString(json, name);
		if (!StringUtils.isNullOrEmpty(value)) {
			return Long.parseLong(value);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a Long value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @return the Long value if it exists, otherwise {@code null}.
	 */
	public static Long getLong(JSONObject jsonObject, String name) {
		return getLong(jsonObject, name, null);
	}

	/**
	 * Retrieve a Long value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the Long value if it exists, otherwise the default value.
	 */
	public static Long getLong(JSONObject jsonObject, String name,
			Long defaultValue) {
		String stringValue = getString(jsonObject, name);
		if (!StringUtils.isNullOrEmpty(stringValue)) {
			return Long.parseLong(stringValue);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Retrieve a String value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @return the String value if it exists, otherwise {@code null}.
	 */
	public static String getString(String json, String name) {
		return getString(json, name, null);
	}

	/**
	 * Retrieve a String value from a JSON String.
	 * 
	 * @param json
	 *            the JSON String.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the String value if it exists, otherwise the default value.
	 */
	public static String getString(String json, String name, String defaultValue) {
		try {
			return getString(new JSONObject(json), name, defaultValue);
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	/**
	 * Retrieve a String value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @return the String value if it exists, otherwise {@code null}.
	 */
	public static String getString(JSONObject jsonObject, String name) {
		return getString(jsonObject, name, null);
	}

	/**
	 * Retrieve a String value from a JSON Object.
	 * 
	 * @param json
	 *            the JSON Object.
	 * @param name
	 *            the name.
	 * @param defaultValue
	 *            the default value.
	 * @return the String value if it exists, otherwise the default value.
	 */
	public static String getString(JSONObject jsonObject, String name,
			String defaultValue) {
		return jsonObject != null && !jsonObject.isNull(name) ? jsonObject
				.optString(name, defaultValue) : defaultValue;
	}

}
